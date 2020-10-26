package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.domain.account.Account;
import com.titanic.airbnbclone.exception.AlreadyReservedException;
import com.titanic.airbnbclone.exception.NoSuchEntityException;
import com.titanic.airbnbclone.repository.AccommodationRepository;
import com.titanic.airbnbclone.repository.AccountRepository;
import com.titanic.airbnbclone.repository.ReservationRepository;
import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.utils.ReservationMessage;
import com.titanic.airbnbclone.utils.StatusEnum;
import com.titanic.airbnbclone.web.dto.request.accommodation.FilterRequest;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationRequest;
import com.titanic.airbnbclone.web.dto.response.accommodation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccountRepository accountRepository;
    private final ReservationRepository reservationRepository;

    public InitAccommodationResponseList getInitInfo() {
        return InitAccommodationResponseList.builder()
                .allData(getInitAccommodation())
                .prices(classifyByPrice())
                .status(String.valueOf(HttpStatus.SC_OK))
                .build();
    }

    @Transactional(readOnly = true)
    public List<InitAccommodationResponse> getInitAccommodation() {
        return accommodationRepository.getInitAccommodation();
    }

    public List<PriceRangeResponse> classifyByPrice() {
        return accommodationRepository.classifyAccommodationPrice();
    }

    /** 지역, 날짜, 인원 기준으로 필터링하는 메서드
     *  필터링 순서 : 지역, 예약인원 -> 예약 유무 -> 예약있는 숙박 중 겹치는 숙박 제거
     */
    public AccommodationResponseList getFiltered(FilterRequest filterRequest) {

        // 1차 필터링 조건(지역, 예약인원)으로 찾은 숙박들
        List<Accommodation> filteredAccommodation = accountRepository.filterAccommodation(filterRequest);

        // 1차 필터링된 숙박 중 예약있는 숙박
        List<Accommodation> reservedAccommodations = filteredAccommodation.stream()
                .filter(Accommodation::hasReservation)
                .collect(Collectors.toList());

        // 예약이 있는 숙박 중에서 요청된 날짜로 예약이 불가능한 숙박들
        List<Accommodation> cannotReserved = reservedAccommodations.stream()
                .filter(accommodation -> !accommodation.isReservable(filterRequest.getStartDate(), filterRequest.getEndDate()))
                .collect(Collectors.toList());

        // 전체 숙박 중 예약 불가능한 숙박 제거하는 과정
        // 예약이 있더라도 현재 날짜와 겹치지 않으면 예약 가능하기 때문
        cannotReserved.stream()
                .map(filteredAccommodation::remove)
                .collect(Collectors.toList());

        List<AccommodationResponse> allData = filteredAccommodation.stream()
                .map(AccommodationResponse::of)
                .collect(Collectors.toList());

        return AccommodationResponseList.builder()
                .status(StatusEnum.SUCCESS.getStatusCode())
                .allData(allData)
                .build();
    }

    /** 예약로직
     *  Account와 Accommodtaion에 Reservation 객체를 저장
     */
    public ReservationResponse reserve(Long accommodationId,
                                       ReservationRequest reservationRequest,
                                       HttpServletRequest request) {

        // 해당 id로 숙박 조회, 없을 경우 해당 숙박이 없다 에러 핸들링
        Accommodation foundAccommodation = accommodationRepository.findByIdWithReservation(accommodationId)
                .orElseThrow(() -> new NoSuchEntityException(accommodationId));

        Account foundAccount = accountRepository.findByEmail(getAccountEmail(request));

        // 요청 날짜가 예약 불가능할 경우 예외 처리
        if (!foundAccommodation.isReservable(reservationRequest.getStartDate(), reservationRequest.getEndDate())) {
            throw new AlreadyReservedException(ReservationMessage.ALREADY_RESERVABLE.getMessage());
        }

        addReservation(foundAccount, foundAccommodation, reservationRequest);
        return ReservationResponse.of(
                StatusEnum.SUCCESS.getStatusCode(),
                ReservationMessage.RESERVATION_SUCCESS.getMessage());
    }

    public DeleteReservationResponse delete(Long reservationId) {
        /*
         * 해당 reservation이 있는 지 파악한다. 없다면 CancelFailException 에러
         * 이유 : DB에 없는 reservationId를 삭제하려면 전체 롤백이 동작하는데 그러면 500에러가 발생.
         *       에러 처리가 불가능한 상황이라 reservationId가 있는지 확인하는 로직 추가
         */
        reservationRepository.isExisted(reservationId);

        accommodationRepository.cancelReservation(reservationId);
        return DeleteReservationResponse.builder()
                .status(StatusEnum.SUCCESS.getStatusCode())
                .message(ReservationMessage.RESERVATION_CANCEL_SUCCESS.getMessage())
                .build();
    }

    /** 사용자의 예약 내역 조회
     */
    public ReservationInfoResponseList getReservationInfo(HttpServletRequest request) {

        Account foundAccount = accountRepository.findByEmail(getAccountEmail(request));
        List<ReservationInfoResponse> allData = new ArrayList<>();

        // 현재 로그인된 사용자의 예약 내역을 확인해 DTO로 만드는 과정
        for (Reservation reservation : foundAccount.getReservations()) {
            Long accommodationId = reservation.getAccommodation().getId();
            Accommodation foundAccommodation = accommodationRepository.findByIdWithPicture(accommodationId)
                    .orElseThrow(() -> new NoSuchEntityException(accommodationId));

            ReservationInfoResponse reservationInfoResponse
                    = ReservationInfoResponse.from(accommodationId, foundAccommodation, reservation);
            allData.add(reservationInfoResponse);
        }

        return ReservationInfoResponseList.builder()
                .status(StatusEnum.SUCCESS.getStatusCode())
                .allData(allData)
                .build();
    }

    // HttpServletRequest에서 유저이메일 꺼내오는 메서드
    private String getAccountEmail(HttpServletRequest request) {
        return (String) request.getAttribute(OauthEnum.USER_EMAIL.getValue());
    }

    /** N:M 관계 매핑
     *  Account에 저장된 Reservation을 Accommodation에 저장해야 한다.
     */
    private void addReservation(Account findAccount,
                                Accommodation findAccommodation,
                                ReservationRequest reservationRequest) {
        Reservation savedReservation = findAccount.addReservation(reservationRequest);
        findAccommodation.addReservation(savedReservation);
        accountRepository.save(findAccount);
        accommodationRepository.save(findAccommodation);
    }
}
