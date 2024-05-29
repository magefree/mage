package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class PhyrexianWurm12DeathtouchToken extends TokenImpl {

    public PhyrexianWurm12DeathtouchToken() {
        super("Phyrexian Wurm Token", "1/2 black Phyrexian Wurm artifact creature token with deathtouch");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.WURM);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(2);
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private PhyrexianWurm12DeathtouchToken(final PhyrexianWurm12DeathtouchToken token) {
        super(token);
    }

    public PhyrexianWurm12DeathtouchToken copy() {
        return new PhyrexianWurm12DeathtouchToken(this);
    }
}
