package com.spring.openai.tools;

import com.spring.openai.entity.HelpDeskTicket;
import com.spring.openai.model.TicketRequest;
import com.spring.openai.service.HelpDeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpDeskTools {

    private final HelpDeskService helpDeskService;

    @Tool(name="createHelpDeskTicket", description = "creates help desk ticket")
    public String createHelpDeskTicket(@ToolParam(description = "request for creating help desk ticket") TicketRequest ticketRequest, ToolContext toolContext) {

        String username = (String) toolContext.getContext().get("username");
        Long id = helpDeskService.createTicket(ticketRequest, username);
        return "Help desk ticket created successfully for user:"+username+" with id:"+id;
    }

    @Tool(name="getHelpDeskTicketList", description = "returns list of help desk ticket")
    public List<HelpDeskTicket> getHelpDeskTicketList(ToolContext toolContext) {

        String username = (String) toolContext.getContext().get("username");
        return helpDeskService.helpDeskTicketListByUsername(username);
    }


}
