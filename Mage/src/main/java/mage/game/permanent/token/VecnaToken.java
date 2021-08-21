package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class VecnaToken extends TokenImpl {

    public VecnaToken() {
        super("Vecna", "Vecna, a legendary 8/8 black Zombie God creature token with indestructible and all triggered abilities of the exiled cards");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.GOD);
        power = new MageInt(8);
        toughness = new MageInt(8);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AFR");
    }

    private VecnaToken(final VecnaToken token) {
        super(token);
    }

    public VecnaToken copy() {
        return new VecnaToken(this);
    }
}
