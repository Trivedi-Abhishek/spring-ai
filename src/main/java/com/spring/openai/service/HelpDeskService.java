package com.spring.openai.service;

import com.spring.openai.entity.HelpDeskTicket;
import com.spring.openai.model.TicketRequest;
import com.spring.openai.repository.HelpDeskTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpDeskService {

    private final HelpDeskTicketRepository helpDeskTicketRepository;

    public Long createTicket(TicketRequest ticketRequest, String username) {

        HelpDeskTicket helpDeskTicket = HelpDeskTicket.builder().username(username).issue(ticketRequest.issue()).status("OPEN")
                .createdAt(LocalDateTime.now()).eta(LocalDateTime.now().plusDays(7)).build();

        return helpDeskTicketRepository.save(helpDeskTicket).getId();
    }

    public List<HelpDeskTicket> helpDeskTicketListByUsername(String username) {
        return helpDeskTicketRepository.findByUsername(username);
    }
}
