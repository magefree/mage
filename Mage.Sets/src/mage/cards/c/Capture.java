
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.condition.common.TargetHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class Capture extends CardImpl {

    public Capture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));

        // Untap that creature.
        this.getSpellAbility().addEffect(new UntapTargetEffect());

        // It gains haste until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));

        // If it has a bounty counter on it, add {R}{R}{R}
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new BasicManaEffect(Mana.RedMana(3)), new TargetHasCounterCondition(CounterType.BOUNTY)));
    }

    private Capture(final Capture card) {
        super(card);
    }

    @Override
    public Capture copy() {
        return new Capture(this);
    }
}
