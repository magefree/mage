
package mage.cards.f;

import java.util.UUID;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public final class ForceDenial extends CardImpl {

    public ForceDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target  spell unless its controller pays {1}.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterUnlessPaysEffect(new GenericManaCost(1)),
                new InvertCondition(HateCondition.instance),
                "Counter target spell unless its controller pays {1}"));

        // <i>Hate</i> &mdash; If an opponent lost life from a source other then combat damage this turn, counter that spell instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterTargetEffect(),
                HateCondition.instance,
                "<br><i>Hate</i> &mdash; If an opponent lost life from a source other than combat damage this turn, counter that spell instead"));
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addWatcher(new LifeLossOtherFromCombatWatcher());

        // Scry 1
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private ForceDenial(final ForceDenial card) {
        super(card);
    }

    @Override
    public ForceDenial copy() {
        return new ForceDenial(this);
    }
}
