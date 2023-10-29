package com.fredrikpedersen.experis_vin_lotteri.persistance;

import com.fredrikpedersen.experis_vin_lotteri.persistance.models.Lottery;
import com.fredrikpedersen.experis_vin_lotteri.persistance.models.LotteryTicket;
import com.fredrikpedersen.experis_vin_lotteri.persistance.models.Wine;
import com.fredrikpedersen.experis_vin_lotteri.persistance.repositories.LotteryRepository;
import com.fredrikpedersen.experis_vin_lotteri.persistance.repositories.LotteryTicketRepository;
import com.fredrikpedersen.experis_vin_lotteri.persistance.repositories.WineRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DbInit {

    private final LotteryRepository lotteryRepository;
    private final LotteryTicketRepository lotteryTicketRepository;
    private final WineRepository wineRepository;

    @EventListener
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final List<Lottery> lotteries = lotteryRepository.findAll();

        lotteries.forEach(lottery -> {
            final Wine cheapWine = Wine.builder().price(100).name("Camp").lottery(lottery).build();
            final Wine alrightWine = Wine.builder().price(200).name("Something something spanish").lottery(lottery).build();
            final Wine expensiveWine = Wine.builder().price(300).name("Something something french").lottery(lottery).build();
            final List<Wine> wines = List.of(cheapWine, alrightWine, expensiveWine);

            final List<LotteryTicket> tickets = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                String phonenumber = null;
                if (i % 2 == 0) {
                    phonenumber = "123456789"; //Simulating some sold tickts
                }

                tickets.add(LotteryTicket.builder().ticketNumber(i).soldToPhonenumber(phonenumber).lottery(lottery).build());
            }

            lottery.setLotteryTickets(tickets);
            lottery.setWines(wines);

            wineRepository.saveAll(wines);
            lotteryTicketRepository.saveAll(tickets);
            lotteryRepository.save(lottery);
        });
    }
}
