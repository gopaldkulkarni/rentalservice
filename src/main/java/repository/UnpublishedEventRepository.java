package repository;

import events.UnpublishedEvent;

import java.util.List;

/**
 * This repository holds all unpublished events so that they can be retried.
 */
public interface UnpublishedEventRepository {
    void saveUnpublishedEvent(UnpublishedEvent unpublishedEvent);
    List<UnpublishedEvent> findAllUnpublishedEvents();
    void deleteUnpublishedEvent(UnpublishedEvent unpublishedEvent);
}

