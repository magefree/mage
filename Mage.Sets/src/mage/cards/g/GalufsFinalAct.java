package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalufsFinalAct extends CardImpl {

    public GalufsFinalAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Until end of turn, target creature gets +1/+0 and gains "When this creature dies, put a number of +1/+1 counters equal to its power on up to one target creature."
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
                .setText("until end of turn, target creature gets +1/+0"));
        Ability ability = new DiesSourceTriggeredAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(), SourcePermanentPowerValue.NOT_NEGATIVE
        ).setText("put a number of +1/+1 counters equal to its power on up to one target creature"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfTurn)
                .setText("and gains \"When this creature dies, put a number of +1/+1 counters " +
                        "equal to its power on up to one target creature.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GalufsFinalAct(final GalufsFinalAct card) {
        super(card);
    }

    @Override
    public GalufsFinalAct copy() {
        return new GalufsFinalAct(this);
    }
}
