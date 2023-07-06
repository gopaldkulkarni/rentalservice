package service;

import static org.junit.jupiter.api.Assertions.*;

import events.Event;
import events.EventPublisher;
import events.UnpublishedEventException;
import repository.UnpublishedEventRepository;
import model.Rental;
import model.RentalRequest;
import model.ReturnRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.RentalRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class DefaultRentalServiceTest {
    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UnpublishedEventRepository unpublishedEventRepository;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private DeadletterService deadletterService;

    private DefaultRentalService rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rentalService = new DefaultRentalService(rentalRepository, unpublishedEventRepository, eventPublisher);
        rentalService.setDeleterService(deadletterService);
    }

    @Test
    void testIsBikeAvailable() {
        Long bikeId = 1L;

        // Add your test case logic here

        assertTrue(rentalService.isBikeAvailable(bikeId));
    }

    @Test
    void testStartRental() throws UnpublishedEventException {
        String tenantId = "tenant1";
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setUserId("12345");

        rentalService.startRental(tenantId, rentalRequest);

        // Verify that the rental is saved
        ArgumentCaptor<Rental> rentalCaptor = ArgumentCaptor.forClass(Rental.class);
        verify(rentalRepository).save(rentalCaptor.capture());
        Rental savedRental = rentalCaptor.getValue();
        assertNotNull(savedRental.getRentalId());
        assertEquals(rentalRequest.getUserId(), savedRental.getUserId());
        assertEquals(rentalRequest.getBikeId(), savedRental.getBikeId());
        assertEquals(tenantId, savedRental.getTenantId());

        // Verify that the event is published
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventPublisher).publish(eventCaptor.capture());
        Event publishedEvent = eventCaptor.getValue();
        assertNotNull(publishedEvent);
        assertEquals("Rental" + savedRental.getRentalId() + " started!", new String(publishedEvent.getEventData()));
    }

    @Test
    void testProcessReturn() throws UnpublishedEventException {
        String rentalId = UUID.randomUUID().toString();
        ReturnRequest returnRequest = new ReturnRequest();

        Rental rental = new Rental();
        rental.setRentalId(rentalId);

        when(rentalRepository.findByRentalId(rentalId)).thenReturn(Optional.of(rental));

        boolean result = rentalService.processReturn(rentalId, returnRequest);

        assertTrue(result);
        assertNotNull(rental.getEndTime());

        // Verify that the rental is saved
        verify(rentalRepository).save(rental);

        // Verify that the event is published
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventPublisher).publish(eventCaptor.capture());
        Event publishedEvent = eventCaptor.getValue();
        assertNotNull(publishedEvent);
        assertEquals("Rental" + rentalId + " Return Requested!", new String(publishedEvent.getEventData()));
    }

    @Test
    void testProcessReturn_ReturnedRentalNotFound() throws UnpublishedEventException{
        String rentalId = UUID.randomUUID().toString();
        ReturnRequest returnRequest = new ReturnRequest();

        when(rentalRepository.findByRentalId(rentalId)).thenReturn(Optional.empty());

        boolean result = rentalService.processReturn(rentalId, returnRequest);

        assertFalse(result);

        // Verify that the rental is not saved
        verify(rentalRepository, never()).save(any(Rental.class));

        // Verify that no event is published
        verify(eventPublisher, never()).publish(any(Event.class));
    }

    // Add more test methods for other methods in the DefaultRentalService class

    @Test
    void testProcessUnpublishedEvents() throws UnpublishedEventException {
       /* UnpublishedEvent unpublishedEvent1 = new UnpublishedEvent("Event1".getBytes());
        UnpublishedEvent unpublishedEvent2 = new UnpublishedEvent("Event2".getBytes());
        List<UnpublishedEvent> unpublishedEvents = List.of(unpublishedEvent1, unpublishedEvent2);

        when(unpublishedEventRepository.findAllUnpublishedEvents()).thenReturn(unpublishedEvents);

        rentalService.processUnpublishedEvents();

        // Verify that each unpublished event is published and then deleted
        verify(eventPublisher, times(2)).publish(any(Event.class));
        verify(unpublishedEventRepository).deleteUnpublishedEvent(unpublishedEvent1);
        verify(unpublishedEventRepository).deleteUnpublishedEvent(unpublishedEvent2);*/
    }
}
