package mage.cards.s;

import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.common.TargetPermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormTheSeedcore extends CardImpl {

    public StormTheSeedcore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Distribute four +1/+1 counter among up to four target creatures you control. Creatures you control gain vigilance and trample until end of turn.
        this.getSpellAbility().addEffect(new DistributeCountersEffect(
                CounterType.P1P1, 4, false,
                "up to four target creatures you control"
        ));
        TargetPermanentAmount target = new TargetCreaturePermanentAmount(4, StaticFilters.FILTER_CONTROLLED_CREATURES);
        target.setMinNumberOfTargets(0);
        target.setMaxNumberOfTargets(4);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("creatures you control gain vigilance"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("and trample until end of turn"));
    }

    private StormTheSeedcore(final StormTheSeedcore card) {
        super(card);
    }

    @Override
    public StormTheSeedcore copy() {
        return new StormTheSeedcore(this);
    }
}
