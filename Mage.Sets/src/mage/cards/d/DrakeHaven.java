
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DrakeToken;

/**
 *
 * @author fireshoes
 */
public final class DrakeHaven extends CardImpl {

    public DrakeHaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When you cycle or discard a card, you may pay {1}. If you do, create a 2/2 blue Drake creature token with flying.
        this.addAbility(new CycleOrDiscardControllerTriggeredAbility(
                new DoIfCostPaid(
                        new CreateTokenEffect(new DrakeToken()),
                        new GenericManaCost(1)))
        );
    }

    private DrakeHaven(final DrakeHaven card) {
        super(card);
    }

    @Override
    public DrakeHaven copy() {
        return new DrakeHaven(this);
    }
}
