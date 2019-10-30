package com.a.spammer.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpamServiceTest {

    @Mock
    AsyncEventClient asyncEventClient;

    @InjectMocks
    SpamService spamService;
    private int spamAmount;
    private List<UUID> actualResponse;
    private ArgumentCaptor<UUID> uuidCaptor;

    @Before
    public void setup() {
        spamAmount = 5;
        uuidCaptor = ArgumentCaptor.forClass(UUID.class);

        when(asyncEventClient.sendRequest(any(UUID.class)))
                .thenAnswer(new Answer<CompletableFuture<ResponseEntity<UUID>>>() {
                    @Override
                    public CompletableFuture<ResponseEntity<UUID>> answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        return CompletableFuture.completedFuture(ResponseEntity.ok((UUID) args[0]));
                    }
                });
    }

    @Test
    public void spamCallsAsyncEventClientCorrectAmountOfTimes() {
        actualResponse = spamService.spam(spamAmount);
        verify(asyncEventClient, times(spamAmount)).sendRequest(uuidCaptor.capture());
        assertEquals(spamAmount, uuidCaptor.getAllValues().size());
    }

    @Test
    public void spamReturnsListOfUuidsSentToAsyncEventClient() {
        actualResponse = spamService.spam(spamAmount);
        verify(asyncEventClient, times(spamAmount)).sendRequest(uuidCaptor.capture());
        List<UUID> uuidsSentToAsyncEventClient = uuidCaptor.getAllValues();
        assertEquals(uuidsSentToAsyncEventClient, actualResponse);
    }

    @Test
    public void spamSwallowsExceptionsCausedByBadFutures(){
        when(asyncEventClient.sendRequest(any(UUID.class)))
                .thenAnswer(new Answer<CompletableFuture<ResponseEntity<UUID>>>() {
                    @Override
                    public CompletableFuture<ResponseEntity<UUID>> answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        return CompletableFuture.completedFuture(null);
                    }
                });
        actualResponse = spamService.spam(spamAmount);
    }

}