package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author weirddan455
 */
public class VampireLifelinkToken extends TokenImpl {

    public VampireLifelinkToken() {
        super("Vampire Token", "2/3 black Vampire creature token with flying and lifelink");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(2);
        toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
    }

    private VampireLifelinkToken(final VampireLifelinkToken token) {
        super(token);
    }

    @Override
    public VampireLifelinkToken copy() {
        return new VampireLifelinkToken(this);
    }
}
