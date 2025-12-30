package com.example.pastebinlite.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TimeProvider {

    @Value("${TEST_MODE:1}")
    private String testMode;

    public long now(HttpServletRequest request) {

        // If TEST_MODE=1, allow tests to control time
        if ("1".equals(testMode)) {
            String header = request.getHeader("x-test-now-ms");
            if (header != null) {
                try {
                    return Long.parseLong(header);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        // Default: real system time
        return System.currentTimeMillis();
    }
}
