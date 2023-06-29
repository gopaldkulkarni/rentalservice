package events;

/**
 * Commong interface to be used by different publishing client implementations.
 */
public interface EventPublisher {
    public void publish(Event eventData) throws UnpublishedEventException;
}
