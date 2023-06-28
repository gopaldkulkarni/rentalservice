# Rental Service

## This is a Micro Service which facilitates following requirements
1. API to create a rental
2. API to start a rental
3. API to end a rental
4. API to get available rental models

## Integration/ High level interaction 
![arch_diag](highlevel.PNG)
The Rental service uses BikeInventoryService (BikeService) to verify the  status of the bike
The Rental Service uses A notification service to notify if the rental is about to expire. 
It publishes every API invocation as an event to provide realtime analytics and monitoring

#### Low level packages
1. Client package - Clients to connect to Bike Service, Kafka, Notification Service
2. util package - Contains common utilities like JSON parsing etc
3. scheduler package - We have two background jobs. 1) Periodically archive the completed/cancelled rental records to keep the QPS support high. 2) Keep checking the rental duration expiry. If near to expiry send the push notification.
