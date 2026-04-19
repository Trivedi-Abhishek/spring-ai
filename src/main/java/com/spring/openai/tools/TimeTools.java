package com.spring.openai.tools;

import com.spring.openai.advisor.TokenUsageAuditAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class TimeTools {

    private final static Logger logger = LoggerFactory.getLogger(TimeTools.class);

    @Tool(name="getCurrentLocalTime", description = "returns the current local time")
    public String getCurrentLocalTime() {
        logger.info("Returning local time");
        return LocalTime.now().toString();
    }

    @Tool(name="getTimeZoneSpecificTime", description = "returns the current time of given timezone")
    public String getTimeZoneSpecificTime(@ToolParam(description="Value representing timezone") String timezone) {

        logger.info("Returning current time of the timezone:{}", timezone);
        return LocalTime.now(ZoneId.of(timezone)).toString();
    }
}
