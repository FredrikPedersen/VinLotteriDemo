package com.fredrikpedersen.experis_vin_lotteri.services.lottery;

import com.fredrikpedersen.experis_vin_lotteri.dtos.BuyTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.CreateLotteryDto;
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
    public LotteryDto createNew(final CreateLotteryDto createLotteryDto) {
        //TODO
        // Create a new lottery with the wines start and end dates from dto
        // Create private method for creating tickets and add to lottery
        // Validate endDate is after startDate
        // Validate wine list is not empty

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED!");
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
                .filter(lotteryTicket -> lotteryTicket.soldToPhonenumber() == null)
                .collect(Collectors.toList());
    }

    @Override
    public LotteryTicketDto buyTicket(final BuyTicketDto buyTicketDto) {
        final LotteryTicket ticket = ticketRepository.findById(buyTicketDto.ticketId())
                .orElseThrow(() -> new NoSuchPersistedEntityException(String.format("Could not find ticket with ID %s", buyTicketDto.ticketId())));

        if (ticket.getSoldToPhonenumber() != null) {
            throw new RuntimeException("Ticket is already sold!");
        }

        final Boolean paymentIsComplete = paymentService.sendVippsPaymentRequest(buyTicketDto.phonenumber());

        if (!paymentIsComplete) {
            throw new RuntimeException("Payment either failed or was cancelled by the user");
        }

        final LotteryTicket updatedTicket = ticketRepository.save(ticket.toBuilder()
                .soldToPhonenumber(buyTicketDto.phonenumber())
                .build()
        );

        return ticketMapper.toDto(updatedTicket);
    }

    @Override
    public List<LotteryTicketDto> closeLottery(final Long lotteryId) {
        //TODO
        // Find lottery by ID
        // For each Wine tied to the lottery, draw a random ticket from the lotteries tickets
        // One ticket can only win once, so create a list of winner tickets and add drawn tickets to that list
        // - The drawn ticket must have soldToPhonenumber != null
        // - The drawn ticket cannot already be in the winners list (unless there are less tickets sold than wines available)
        // Send sms to the phonenumber associated with the ticket and tell them they've won
        // Return the list of winning tickets

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED!");
    }
}