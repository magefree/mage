package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author DarioBarrabinoDev
 */
public final class RavageToken extends TokenImpl {

    public RavageToken() {
        super("Ravage", "Ravage, a legendary 3/3 black Robot artifact creature token with menace and deathtouch");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ROBOT);
        power = new MageInt(3);
        toughness = new MageInt(3);

        this.addAbility(new MenaceAbility(false));
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private RavageToken(final RavageToken token) {
        super(token);
    }

    public RavageToken copy() {
        return new RavageToken(this);
    }
}
