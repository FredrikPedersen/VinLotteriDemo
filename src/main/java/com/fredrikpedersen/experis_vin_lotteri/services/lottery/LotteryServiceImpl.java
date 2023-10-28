package com.fredrikpedersen.experis_vin_lotteri.services.lottery;

import com.fredrikpedersen.experis_vin_lotteri.dtos.BuyTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.exceptions.NoSuchPersistedEntityException;
import com.fredrikpedersen.experis_vin_lotteri.mappers.LotteryMapper;
import com.fredrikpedersen.experis_vin_lotteri.mappers.LotteryTicketMapper;
import com.fredrikpedersen.experis_vin_lotteri.persistance.models.Lottery;
import com.fredrikpedersen.experis_vin_lotteri.persistance.models.LotteryTicket;
import com.fredrikpedersen.experis_vin_lotteri.persistance.repositories.LotteryRepository;
import com.fredrikpedersen.experis_vin_lotteri.persistance.repositories.LotteryTicketRepository;
import com.fredrikpedersen.experis_vin_lotteri.services.CrudServiceImpl;
import com.fredrikpedersen.experis_vin_lotteri.services.payment.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotteryServiceImpl extends CrudServiceImpl<LotteryDto, Lottery> implements LotteryService {

    private final LotteryTicketRepository ticketRepository;
    private final LotteryTicketMapper ticketMapper;
    private final PaymentService paymentService;

    public LotteryServiceImpl(final LotteryRepository repository,
                              final LotteryTicketRepository ticketRepository,
                              final LotteryMapper dtoMapper,
                              final LotteryTicketMapper ticketMapper,
                              final PaymentService paymentService) {
        super(dtoMapper, repository);
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.paymentService = paymentService;
    }

    @Override
    public List<LotteryDto> findAllNonExpiredLotteries() {
        return findAll().stream()
                .filter(lottery -> lottery.endTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LotteryTicketDto> findAllAvailableTicketForLottery(final Long lotteryId) {
        return findById(lotteryId).lotteryTickets().stream()
                .filter(lotteryTicket -> lotteryTicket.soldToUsername() == null)
                .collect(Collectors.toList());
    }

    @Override
    public LotteryTicketDto buyTicket(final BuyTicketDto buyTicketDto) {
        final LotteryTicket ticket = ticketRepository.findById(buyTicketDto.ticketId())
                .orElseThrow(() -> new NoSuchPersistedEntityException(String.format("Could not find ticket with ID %s", buyTicketDto.ticketId())));

        if (ticket.getSoldToUsername() != null) {
            throw new RuntimeException("Ticket is already sold!");
        }

        final Boolean paymentIsComplete = paymentService.sendVippsPaymentRequest(buyTicketDto.phonenumber());

        if (!paymentIsComplete) {
            throw new RuntimeException("Payment either failed or was cancelled by the user");
        }

        final LotteryTicket updatedTicket = ticketRepository.save(ticket.toBuilder()
                .soldToUsername(buyTicketDto.username())
                .build()
        );

        return ticketMapper.toDto(updatedTicket);
    }
}