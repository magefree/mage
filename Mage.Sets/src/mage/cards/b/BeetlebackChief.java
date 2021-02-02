
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author LevelX2
 */
public final class BeetlebackChief extends CardImpl {

    public BeetlebackChief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Beetleback Chief enters the battlefield, create two 1/1 red Goblin creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 2)));

    }

    private BeetlebackChief(final BeetlebackChief card) {
        super(card);
    }

    @Override
    public BeetlebackChief copy() {
        return new BeetlebackChief(this);
    }
}
