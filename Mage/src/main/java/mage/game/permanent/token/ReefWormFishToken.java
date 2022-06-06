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
public final class ReefWormFishToken extends TokenImpl {

    public ReefWormFishToken() {
        super("Fish Token", "3/3 blue Fish creature token with \"When this creature dies, create a 6/6 blue Whale creature token with \"When this creature dies, create a 9/9 blue Kraken creature token.\"\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.FISH);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ReefWormWhaleToken())));

        availableImageSetCodes = Arrays.asList("C14", "A25", "C21");
    }

    public ReefWormFishToken(final ReefWormFishToken token) {
        super(token);
    }

    public ReefWormFishToken copy() {
        return new ReefWormFishToken(this);
    }
}
