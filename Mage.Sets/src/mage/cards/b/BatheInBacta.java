
package mage.cards.b;

import java.util.UUID;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public final class BatheInBacta extends CardImpl {

    public BatheInBacta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // You gain 6 life. If you lost life from a source other than combat damage this turn, you gain 9 life instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(6), new GainLifeEffect(9),
                new InvertCondition(HateCondition.instance),
                "You gain 6 life. If you lost life from a source other than combat damage this turn, you gain 9 life instead"));
        this.getSpellAbility().addWatcher(new LifeLossOtherFromCombatWatcher());
    }

    private BatheInBacta(final BatheInBacta card) {
        super(card);
    }

    @Override
    public BatheInBacta copy() {
        return new BatheInBacta(this);
    }
}
