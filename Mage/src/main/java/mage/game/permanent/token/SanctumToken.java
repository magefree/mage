package mage.game.permanent.token;

import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class SanctumToken extends TokenImpl {

    public SanctumToken() {
        super("Sanctum", "land token named Sanctum with \"{T}: Add one mana of any color.\"");
        cardType.add(CardType.LAND);
        this.addAbility(new AnyColorManaAbility());
    }

    private SanctumToken(final SanctumToken token) {
        super(token);
    }

    public SanctumToken copy() {
        return new SanctumToken(this);
    }
}
