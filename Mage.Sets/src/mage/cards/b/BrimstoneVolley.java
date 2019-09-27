
package mage.cards.b;

import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.MorbidWatcher;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class BrimstoneVolley extends CardImpl {

    public BrimstoneVolley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Brimstone Volley deals 3 damage to any target.
        // <i>Morbid</i> &mdash; Brimstone Volley deals 5 damage to that creature or player instead if a creature died this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(3), new DamageTargetEffect(5), HellbentCondition.instance,
                "{this} deals 3 damage to any target." +
                        "<br><i>Morbid</i> &mdash; {this} deals 5 damage instead if a creature died this turn."
        ));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addWatcher(new MorbidWatcher());
    }

    public BrimstoneVolley(final BrimstoneVolley card) {
        super(card);
    }

    @Override
    public BrimstoneVolley copy() {
        return new BrimstoneVolley(this);
    }
}
