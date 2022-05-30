package mage.cards.o;

import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverwhelmingEncounter extends CardImpl {

    public OverwhelmingEncounter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Creatures you control gain vigilance and trample until end of turn. Roll a d20.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("creatures you control gain vigilance"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and trample until end of turn"));
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();

        // 1-9 | Creatures you control get +2/+2 until end of turn.
        effect.addTableEntry(1, 9, new BoostControlledEffect(2, 2, Duration.EndOfTurn));

        // 10-19 | Put two +1/+1 counters on each creature you control.
        effect.addTableEntry(10, 19, new AddCountersAllEffect(
                CounterType.P1P1.createInstance(2), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        // 20 | Put four +1/+1 counters on each creature you control.
        effect.addTableEntry(20, 20, new AddCountersAllEffect(
                CounterType.P1P1.createInstance(4), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        this.getSpellAbility().addEffect(effect);
    }

    private OverwhelmingEncounter(final OverwhelmingEncounter card) {
        super(card);
    }

    @Override
    public OverwhelmingEncounter copy() {
        return new OverwhelmingEncounter(this);
    }
}
