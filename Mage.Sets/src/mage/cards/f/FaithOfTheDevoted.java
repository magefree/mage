
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Styxo
 */
public final class FaithOfTheDevoted extends CardImpl {

    public FaithOfTheDevoted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Whenever you cycle or discard a card, you may pay {1}. If you do, each opponent loses 2 life and you gain 2 life.
        Effect subEffect = new GainLifeEffect(2);
        subEffect.setText("and you gain 2 life");

        DoIfCostPaid effect = new DoIfCostPaid(
                new LoseLifeOpponentsEffect(2),
                new GenericManaCost(1));
        effect.addEffect(subEffect);

        this.addAbility(new CycleOrDiscardControllerTriggeredAbility(effect));
    }

    private FaithOfTheDevoted(final FaithOfTheDevoted card) {
        super(card);
    }

    @Override
    public FaithOfTheDevoted copy() {
        return new FaithOfTheDevoted(this);
    }
}
