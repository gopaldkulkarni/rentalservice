package pricing;

import model.Rental;

/**
 * The interface defines generic pricing calculation. Possible implementation could be by Where is My Cab, or Implementation based on ExperimentService
 */
public interface ChargeService {
    public double calculate(Rental rental);
}
