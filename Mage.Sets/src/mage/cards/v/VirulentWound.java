package mage.cards.v;

import java.util.UUID;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class VirulentWound extends CardImpl {

    public VirulentWound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Put a -1/-1 counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // When that creature dies this turn, its controller gets a poison counter.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new WhenTargetDiesDelayedTriggeredAbility(
                new AddCountersTargetEffect(CounterType.POISON.createInstance()).setText("its controller gets a poison counter"),
                SetTargetPointer.PLAYER
        )));
    }

    private VirulentWound(final VirulentWound card) {
        super(card);
    }

    @Override
    public VirulentWound copy() {
        return new VirulentWound(this);
    }
}
