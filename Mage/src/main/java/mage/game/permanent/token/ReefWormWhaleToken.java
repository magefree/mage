package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class ReefWormWhaleToken extends TokenImpl {

    public ReefWormWhaleToken() {
        super("Whale Token", "6/6 blue Whale creature token with \"When this creature dies, create a 9/9 blue Kraken creature token.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.WHALE);
        power = new MageInt(6);
        toughness = new MageInt(6);

        addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new Kraken99Token())));

        availableImageSetCodes.addAll(Arrays.asList("C14", "C21"));
    }

    public ReefWormWhaleToken(final ReefWormWhaleToken token) {
        super(token);
    }

    public ReefWormWhaleToken copy() {
        return new ReefWormWhaleToken(this);
    }
}
