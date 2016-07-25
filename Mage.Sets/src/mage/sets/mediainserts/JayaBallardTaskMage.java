package mage.sets.mediainserts;

import java.util.UUID;

public class JayaBallardTaskMage extends mage.sets.timespiral.JayaBallardTaskMage {
    
    public JayaBallardTaskMage(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "18";
        this.expansionSetCode = "MBP";
    }
    
    public JayaBallardTaskMage(final JayaBallardTaskMage card) {
        super(card);
    }
    
    @Override
    public JayaBallardTaskMage copy() {
        return new JayaBallardTaskMage(this);
    }
}
