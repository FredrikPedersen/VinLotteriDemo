package com.fredrikpedersen.experis_vin_lotteri.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fredrikpedersen.experis_vin_lotteri.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
class LotteryControllerIntegrationTest extends MockMvcTest {

    private final String BASE_URL = "/lottery";

    @Nested
    class getNonExpiredLotteries {

        @Test
        void returnsLotteryList() throws Exception {
            final MvcResult result = mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andReturn();

            final List<LotteryDto> responseDtos = parseResponseList(result);
            assertEquals(responseDtos.size(), 3);
        }
    }

    //TODO Not implemented yet, will fail
    @Nested
    class createNew {

        private List<WineDto> wines;

        @BeforeEach
        void setUp() {
            wines = List.of(
                    WineDto.builder().price(100).name("cheap").build(),
                    WineDto.builder().price(500).name("expensive").build()
            );
        }

        @Test
        void dtoWithValidValues_returnsLotteryWithValuesFromDto() throws Exception {
            final LocalDateTime expectedStarttime = LocalDateTime.now();
            final LocalDateTime expectedEndtime = expectedStarttime.plusDays(5);
            final CreateLotteryDto requestBody = CreateLotteryDto.builder()
                    .startTime(expectedStarttime)
                    .endTime(expectedEndtime)
                    .wines(wines)
                    .build();

            final MvcResult result = mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(requestBody)))
                    .andExpect(status().isCreated())
                    .andReturn();

            final LotteryDto responseDto = parseResponseData(result, LotteryDto.class);
            assertAll("Asserting valid LotteryDto values",
                    () -> assertEquals(expectedStarttime, responseDto.startTime()),
                    () -> assertEquals(expectedEndtime, responseDto.endTime()),
                    () -> assertEquals(wines, responseDto.wines()));
        }

        @Test
        void dtoWithInvalidStartAndEndTimes_returnsInternalServerError() throws Exception {
            final LocalDateTime endTime = LocalDateTime.now();
            final LocalDateTime startTime = endTime.plusDays(5);
            final CreateLotteryDto requestBody = CreateLotteryDto.builder()
                    .startTime(startTime)
                    .endTime(endTime)
                    .wines(wines)
                    .build();

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(requestBody)))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void dtoWithEmptyWineList_returnsInternalServerError() throws Exception {
            final List<WineDto> emptyWineList = Collections.emptyList();
            final LocalDateTime startTime = LocalDateTime.now();
            final LocalDateTime endTime = startTime.plusDays(5);
            final CreateLotteryDto requestBody = CreateLotteryDto.builder()
                    .startTime(startTime)
                    .endTime(endTime)
                    .wines(emptyWineList)
                    .build();

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(requestBody)))
                    .andExpect(status().isInternalServerError());
        }
    }

    //TODO Not implemented yet, will fail
    // Will have to seed some sold tickets associated with the given lotteries for most of these cases
    @Nested
    class closeLottery {

        @Test
        void withExistingLotteryId_returnsListOfWinningTickets() throws Exception {
            final MvcResult result = mockMvc.perform(post(BASE_URL + "/closeLottery/" + 1))
                    .andExpect(status().isOk())
                    .andReturn();

            final List<LotteryTicketDto> responseDto = parseResponseList(result);
            assertEquals(responseDto.size(), 3);
        }

        @Test
        void lessTicketsSoldThanWinesAvailable_returnsListOfWinningTickets() throws Exception {
            final MvcResult result = mockMvc.perform(post(BASE_URL + "/closeLottery/" + 2))
                    .andExpect(status().isOk())
                    .andReturn();

            final List<LotteryTicketDto> responseDto = parseResponseList(result);
            assertEquals(responseDto.size(), 2);

        }

        @Test
        void withNonExistingLotteryId_returnsBadRequest() throws Exception {
            mockMvc.perform(post(BASE_URL + "/closeLottery/" + 100))
                    .andExpect(status().isBadRequest())
                    .andReturn();
        }
    }


    @Nested
    class getAvailableTicketsForLottery {

        @Test
        void withValidLotteryId_returnsListOfAllAvailableTickets() throws Exception {
            final MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/tickets/" + 1))
                    .andExpect(status().isOk())
                    .andReturn();

            final List<LotteryTicketDto> responseDto = parseResponseList(mvcResult);
            assertEquals(50, responseDto.size());
        }

        @Test
        void withInvalidValidLotteryId_returnsBadRequest() throws Exception {
           mockMvc.perform(get(BASE_URL + "/tickets/" + 100))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class buyTicket {

        @Test
        void dtoWithValidValues_returnsSoldTicket() throws Exception {
            final Long expectedTicketId = 1L;
            final String expectedPhonenumber = "123456789";
            final BuyTicketDto requestBody = BuyTicketDto.builder()
                    .ticketId(expectedTicketId)
                    .phonenumber(expectedPhonenumber)
                    .build();

            final MvcResult mvcResult = mockMvc.perform(post(BASE_URL + "/tickets/buy/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(requestBody)))
                    .andExpect(status().isCreated())
                    .andReturn();

            final LotteryTicketDto responseDto = parseResponseData(mvcResult, LotteryTicketDto.class);
            assertEquals(responseDto.soldToPhonenumber(), expectedPhonenumber);
        }

        @Test
        void dtoWithInvalidId_returnsBadRequest() throws Exception {
            final BuyTicketDto requestBody = BuyTicketDto.builder()
                    .ticketId(-1L)
                    .phonenumber("123456789")
                    .build();

            mockMvc.perform(post(BASE_URL + "/tickets/buy/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapToJson(requestBody)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void dtoWithIdToAlreadySoldTicket_returnsInternalServerError() throws Exception {
            final BuyTicketDto requestBody = BuyTicketDto.builder()
                    .ticketId(2L)
                    .phonenumber("123456789")
                    .build();

            mockMvc.perform(post(BASE_URL + "/tickets/buy/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(requestBody)))
                    .andExpect(status().isInternalServerError());
        }
    }
}