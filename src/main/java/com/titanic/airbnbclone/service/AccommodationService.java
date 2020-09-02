package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.domain.account.Account;
import com.titanic.airbnbclone.exception.AlreadyReservationException;
import com.titanic.airbnbclone.exception.NoSuchEntityException;
import com.titanic.airbnbclone.repository.AccommodationRepository;
import com.titanic.airbnbclone.repository.AccountRepository;
import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.utils.ReservationEnum;
import com.titanic.airbnbclone.utils.StatusEnum;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationDemandDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.*;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccountRepository accountRepository;

    public AccommodationResponseDtoList getInitInfo() {
        return AccommodationResponseDtoList.builder()
                .allData(getInitAccommodation())
                .prices(classifyAccommodationPrice())
                .status(String.valueOf(HttpStatus.SC_OK))
                .build();
    }

    public List<PriceRangeResponseDto> classifyAccommodationPrice() {
        return accommodationRepository.classifyAccommodationPrice();
    }

    public List<AccommodationResponseDto> getInitAccommodation() {
        return accommodationRepository.getInitAccommodation();
    }

    public ReservationResponseDto reserve(Long accommodationId,
                                          ReservationDemandDto reservationDemandDto,
                                          HttpServletRequest request) {
        try {
            String userEmail = (String) request.getAttribute(OauthEnum.USER_EMAIL.getValue());
            Accommodation findAccommodation = accommodationRepository.findOne(accommodationId)
                    .orElseThrow(() -> new NoSuchEntityException(accommodationId));

            if (!findAccommodation.isReservable(reservationDemandDto)) {
                throw new AlreadyReservationException(ReservationEnum.ALREADY_RESERVABLE.getMessage());
            }

            Account findAccount = accountRepository.findByEmail(userEmail);
            Reservation savedReservation = findAccount.addReservation(reservationDemandDto);
            findAccommodation.addReservation(savedReservation);
            accountRepository.save(findAccount);
            accommodationRepository.save(findAccommodation);

            return ReservationResponseDto.builder()
                    .status(StatusEnum.SUCCESS.getStatusCode())
                    .message(ReservationEnum.RESERVATION_SUCCESS.getMessage())
                    .build();

        } catch (AlreadyReservationException e) {
            return ReservationResponseDto.builder()
                    .status(StatusEnum.ACCEPTED.getStatusCode())
                    .message(ReservationEnum.ALREADY_RESERVABLE.getMessage())
                    .build();
        } catch (Exception e) {
            return ReservationResponseDto.builder()
                    .status(StatusEnum.ACCEPTED.getStatusCode())
                    .message(ReservationEnum.RESERVATION_FAIL.getMessage())
                    .build();
        }
    }

    public DeleteReservationResponseDto delete(Long accommodationId, Long reservationId, HttpServletRequest request) {
        try {
            accommodationRepository.cancelReservation(reservationId);

            return DeleteReservationResponseDto.builder()
                    .status(StatusEnum.SUCCESS.getStatusCode())
                    .message(ReservationEnum.RESERVATION_CANCEL_SUCCESS.getMessage())
                    .build();

        } catch (Exception e) {
            return DeleteReservationResponseDto.builder()
                    .status(StatusEnum.ACCEPTED.getStatusCode())
                    .message(ReservationEnum.RESERVATION_CANCEL_FAIL.getMessage())
                    .build();
        }
    }
}
