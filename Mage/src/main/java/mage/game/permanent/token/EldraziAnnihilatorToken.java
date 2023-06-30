package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class EldraziAnnihilatorToken extends TokenImpl {

    public EldraziAnnihilatorToken() {
        super("Eldrazi Token", "7/7 colorless Eldrazi creature token with annihilator 1");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI);
        power = new MageInt(7);
        toughness = new MageInt(7);
        addAbility(new AnnihilatorAbility(1));
    }

    public EldraziAnnihilatorToken(final EldraziAnnihilatorToken token) {
        super(token);
    }

    public EldraziAnnihilatorToken copy() {
        return new EldraziAnnihilatorToken(this);
    }
}
