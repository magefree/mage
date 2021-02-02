
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author TheElk801
 */
public final class GoblinInstigator extends CardImpl {

    public GoblinInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Goblin Instigator enters the battlefield, create a 1/1 red Goblin creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinToken())));
    }

    private GoblinInstigator(final GoblinInstigator card) {
        super(card);
    }

    @Override
    public GoblinInstigator copy() {
        return new GoblinInstigator(this);
    }
}
