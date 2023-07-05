package model;


public class RentalRequest {
    private String userId;
    private String bikeId;
    private int duration;

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getRentalId() {
        return rentalId;
    }

    private int rentalId;

    // Getters and setters for the fields

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

