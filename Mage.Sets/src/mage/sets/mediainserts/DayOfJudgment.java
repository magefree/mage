package mage.sets.mediainserts;

import java.util.UUID;

public class DayOfJudgment extends mage.sets.zendikar.DayOfJudgment {
    
    public DayOfJudgment(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "22";
        this.expansionSetCode = "MBP";
    }
    
    public DayOfJudgment(final DayOfJudgment card) {
        super(card);
    }
    
    @Override
    public DayOfJudgment copy() {
        return new DayOfJudgment(this);
    }
}
