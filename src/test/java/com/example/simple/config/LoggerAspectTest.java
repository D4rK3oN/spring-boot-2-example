package com.example.simple.config;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggerAspectTest {

    private static final String HEADER_TRACE = " : Invoked class [LoggerAspectTest$TestService$MockitoMock$";
    private static final String INPUT_TRACE = ".methodName] | Input Args [EMPTY]";
    private static final String OUTPUT_TRACE = ".methodName] | Output Response [[LoggerAspectTest.Response(id=00, name=Test)]]";

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature signature;

    @Mock
    private TestService testService;

    @InjectMocks
    private LoggerAspect loggerAspect;

    @Test
    void testPrintTraces() throws Throwable {
        when(joinPoint.getTarget()).thenReturn(testService);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getSignature().getName()).thenReturn("methodName");
        when(joinPoint.getArgs()).thenReturn(new String[0]);
        when(joinPoint.proceed()).thenReturn(List.of(new Response("00", "Test")));

        // Create a stream to hold the output
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PrintStream oldPrintStream = System.out;

        // Tell Java to use your special stream
        System.setOut(new PrintStream(byteArrayOutputStream));

        loggerAspect.printTraces(joinPoint);

        final var trace = byteArrayOutputStream.toString();

        // Restore old output
        System.setOut(oldPrintStream);

        // Print byteArrayOutputStream message in the console
        System.out.println(trace);

        assertAll(
                () -> assertEquals(2, StringUtils.countOccurrencesOf(trace, HEADER_TRACE)),
                () -> assertTrue(trace.contains(INPUT_TRACE)),
                () -> assertTrue(trace.contains(OUTPUT_TRACE))
        );
    }

    private interface TestService {
    }

    @AllArgsConstructor
    @ToString
    private class Response {
        private String id;
        private String name;
    }
}