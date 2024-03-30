package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class VampireRogueToken extends TokenImpl {

    public VampireRogueToken() {
        super("Vampire Rogue Token", "black Vampire Rogue creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        subtype.add(SubType.ROGUE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(LifelinkAbility.getInstance());
    }

    private VampireRogueToken(final VampireRogueToken token) {
        super(token);
    }

    @Override
    public VampireRogueToken copy() {
        return new VampireRogueToken(this);
    }
}
