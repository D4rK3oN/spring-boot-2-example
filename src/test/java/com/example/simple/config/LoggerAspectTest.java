package com.example.simple.config;

import com.example.simple.domain.Simple;
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

    private final String headerTrace = " : Invoked class [LoggerAspectTest$TestService$MockitoMock$";
    private final String inputTrace = ".methodName] | Input Args [EMPTY]";
    private final String outputTrace = ".methodName] | Output Response [[Simple(documentId=null, id=00, name=Test)]]";

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
        when(joinPoint.proceed()).thenReturn(List.of(Simple.builder().id("00").name("Test").build()));

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
                () -> assertEquals(2, StringUtils.countOccurrencesOf(trace, headerTrace)),
                () -> assertTrue(trace.contains(inputTrace)),
                () -> assertTrue(trace.contains(outputTrace))
        );
    }

    private interface TestService {
    }
}