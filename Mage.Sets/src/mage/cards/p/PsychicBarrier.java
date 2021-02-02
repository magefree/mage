
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class PsychicBarrier extends CardImpl {

    public PsychicBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Counter target creature spell. Its controller loses 1 life.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(1));
    }

    private PsychicBarrier(final PsychicBarrier card) {
        super(card);
    }

    @Override
    public PsychicBarrier copy() {
        return new PsychicBarrier(this);
    }
}
