
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ReefWormFishToken;

/**
 *
 * @author LevelX2
 */
public final class ReefWorm extends CardImpl {

    public ReefWorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.WORM);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // When Reef Worm dies, create a 3/3 blue Fish creature token with "When this creature dies, create a 6/6 blue Whale creature token with "When this creature dies, create a 9/9 blue Kraken creature token.""
        addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ReefWormFishToken())));
    }

    private ReefWorm(final ReefWorm card) {
        super(card);
    }

    @Override
    public ReefWorm copy() {
        return new ReefWorm(this);
    }
}
