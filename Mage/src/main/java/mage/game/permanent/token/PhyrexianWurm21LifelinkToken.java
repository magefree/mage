package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class PhyrexianWurm21LifelinkToken extends TokenImpl {

    public PhyrexianWurm21LifelinkToken() {
        super("Phyrexian Wurm Token", "2/1 black Phyrexian Wurm artifact creature token with lifelink");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.WURM);
        color.setBlack(true);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(LifelinkAbility.getInstance());
    }

    private PhyrexianWurm21LifelinkToken(final PhyrexianWurm21LifelinkToken token) {
        super(token);
    }

    public PhyrexianWurm21LifelinkToken copy() {
        return new PhyrexianWurm21LifelinkToken(this);
    }
}
