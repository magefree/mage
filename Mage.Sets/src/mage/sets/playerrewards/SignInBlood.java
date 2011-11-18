package mage.sets.playerrewards;

import java.util.UUID;

/**
 * @author Loki
 */
public class SignInBlood extends mage.sets.magic2010.SignInBlood {
    public SignInBlood(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 42;
        this.expansionSetCode = "MPR";
    }

    public SignInBlood(final SignInBlood card) {
        super(card);
    }

    @Override
    public SignInBlood copy() {
        return new SignInBlood(this);
    }
}
