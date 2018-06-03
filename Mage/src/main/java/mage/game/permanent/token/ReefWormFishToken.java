
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;

/**
 *
 * @author spjspj
 */
public final class ReefWormFishToken extends TokenImpl {

    public ReefWormFishToken() {
        super("Fish", "3/3 blue Fish creature token with \"When this creature dies, create a 6/6 blue Whale creature token with \"When this creature dies, create a 9/9 blue Kraken creature token.\"\"");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.FISH);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new ReefWormWhaleToken())));
    }

    public ReefWormFishToken(final ReefWormFishToken token) {
        super(token);
    }

    public ReefWormFishToken copy() {
        return new ReefWormFishToken(this);
    }
}
