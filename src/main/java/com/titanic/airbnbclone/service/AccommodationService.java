package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.domain.account.Account;
import com.titanic.airbnbclone.exception.NoSuchEntityException;
import com.titanic.airbnbclone.repository.AccommodationRepository;
import com.titanic.airbnbclone.repository.AccountRepository;
import com.titanic.airbnbclone.repository.ReservationRepository;
import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.utils.ReservationMessage;
import com.titanic.airbnbclone.utils.StatusEnum;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationDemandDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.*;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccountRepository accountRepository;
    private final ReservationRepository reservationRepository;

    public AccommodationResponseDtoList getInitInfo() {
        return AccommodationResponseDtoList.builder()
                .allData(getInitAccommodation())
                .prices(classifyByPrice())
                .status(String.valueOf(HttpStatus.SC_OK))
                .build();
    }

    public List<PriceRangeResponseDto> classifyByPrice() {
        return accommodationRepository.classifyAccommodationPrice();
    }

    @Transactional(readOnly = true)
    public List<AccommodationResponseDto> getInitAccommodation() {
        return accommodationRepository.getInitAccommodation();
    }

    public ReservationResponseDto reserve(Long accommodationId,
                                          ReservationDemandDto reservationDemandDto,
                                          HttpServletRequest request) {
        Accommodation foundAccommodation = accommodationRepository.findOneWithReservations(accommodationId)
                .orElseThrow(() -> new NoSuchEntityException(accommodationId));
        Account foundAccount = accountRepository.findByEmail(getAccountEmail(request));

        foundAccommodation.isReservable(reservationDemandDto);

        addReservation(foundAccount, foundAccommodation, reservationDemandDto);
        return ReservationResponseDto.of(
                StatusEnum.SUCCESS.getStatusCode(),
                ReservationMessage.RESERVATION_SUCCESS.getMessage());
    }

    public DeleteReservationResponseDto delete(Long accommodationId, Long reservationId, HttpServletRequest request) {
        /*
         * 해당 reservation이 있는 지 파악한다. 없다면 CancelFailException 에러
         * 이유 : DB에 없는 reservationId를 삭제하려면 전체 롤백이 동작하는데 그러면 500에러가 발생.
         *       에러 처리가 불가능한 상황이라 reservationId가 있는지 확인하는 로직 추가
         */
        reservationRepository.isExisted(reservationId);

        accommodationRepository.cancelReservation(reservationId);
        return DeleteReservationResponseDto.builder()
                .status(StatusEnum.SUCCESS.getStatusCode())
                .message(ReservationMessage.RESERVATION_CANCEL_SUCCESS.getMessage())
                .build();
    }

    public ReservationInfoResponseDtoList getReservationInfo(HttpServletRequest request) {

        try {
            Account foundAccount = accountRepository.findByEmail(getAccountEmail(request));
            List<ReservationInfoResponseDto> allData = new ArrayList<>();

            // 현재 로그인된 사용자의 예약 내역을 확인해 DTO로 만드는 과정
            for (Reservation reservation : foundAccount.getReservations()) {
                Long accommodationId = reservation.getAccommodation().getId();
                Accommodation foundAccommodation = accommodationRepository.findOneWithPictures(accommodationId)
                        .orElseThrow(() -> new NoSuchEntityException(accommodationId));

                ReservationInfoResponseDto reservationInfoResponseDto
                        = ReservationInfoResponseDto.builder()
                        .accommodationId(accommodationId)
                        .hotelName(foundAccommodation.getName())
                        .reservation(AccountReservationResponseDto.of(reservation))
                        .urls(foundAccommodation.getPictures())
                        .build();
                allData.add(reservationInfoResponseDto);
            }

            return ReservationInfoResponseDtoList.builder()
                    .status(StatusEnum.SUCCESS.getStatusCode())
                    .allData(allData)
                    .build();
        } catch (Exception e) {
            return ReservationInfoResponseDtoList.builder()
                    .status(StatusEnum.ACCEPTED.getStatusCode())
                    .build();
        }
    }

    // HttpServletRequest에서 유저이메일 꺼내오는 메서드
    private String getAccountEmail(HttpServletRequest request) {
        return (String) request.getAttribute(OauthEnum.USER_EMAIL.getValue());
    }

    private void addReservation(Account findAccount,
                                Accommodation findAccommodation,
                                ReservationDemandDto reservationDemandDto) {
        Reservation savedReservation = findAccount.addReservation(reservationDemandDto);
        findAccommodation.addReservation(savedReservation);
        accountRepository.save(findAccount);
        accommodationRepository.save(findAccommodation);
    }
}
