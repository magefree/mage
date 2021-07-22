package mage.cards.e;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExpandedAnatomy extends CardImpl {

    public ExpandedAnatomy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}");

        this.subtype.add(SubType.LESSON);

        // Put two +1/+1 counters on target creature. It gains vigilance until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains vigilance until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ExpandedAnatomy(final ExpandedAnatomy card) {
        super(card);
    }

    @Override
    public ExpandedAnatomy copy() {
        return new ExpandedAnatomy(this);
    }
}
