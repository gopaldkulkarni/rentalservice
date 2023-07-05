package service;

import events.Event;
import events.UnpublishedEvent;

/**
 * A service to move the events to dead letter Queue.
 */
public interface DeadletterService {
    public void move(UnpublishedEvent unpublishedEvent);
}
