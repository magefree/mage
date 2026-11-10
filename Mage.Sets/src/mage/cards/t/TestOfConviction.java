package mage.cards.t;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class TestOfConviction extends CardImpl {

    public TestOfConviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Choose one --
        // * Battle Stations -- Put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
            CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("put a +1/+1 counter on each creature you control"));
        this.getSpellAbility().withFirstModeFlavorWord("Battle Stations");

        // * Begin Negotiations -- Target opponent draws a card. You draw two cards.
        Mode mode = new Mode(new DrawCardTargetEffect(1));
        mode.addEffect(new DrawCardSourceControllerEffect(2, true));
        mode.addTarget(new TargetOpponent());
        mode.withFlavorWord("Begin Negotiations");
        this.getSpellAbility().addMode(mode);
    }

    private TestOfConviction(final TestOfConviction card) {
        super(card);
    }

    @Override
    public TestOfConviction copy() {
        return new TestOfConviction(this);
    }
}
