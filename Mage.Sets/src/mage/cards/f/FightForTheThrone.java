package mage.cards.f;

import mage.MageObjectReference;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class FightForTheThrone extends CardImpl {

    public FightForTheThrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Put a +1/+1 counter on target creature you control. Then it fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText("Then it fights target creature an opponent controls"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // When the creature an opponent controls dies this turn, if you control your commander, you become the monarch.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new FightForTheThroneTriggeredAbility()));
        this.getSpellAbility().addHint(MonarchHint.instance);
    }

    private FightForTheThrone(final FightForTheThrone card) {
        super(card);
    }

    @Override
    public FightForTheThrone copy() {
        return new FightForTheThrone(this);
    }
}

class FightForTheThroneTriggeredAbility extends WhenTargetDiesDelayedTriggeredAbility {

    FightForTheThroneTriggeredAbility() {
        super(new BecomesMonarchSourceEffect());
        setTriggerPhrase("When the creature an opponent controls dies this turn, ");
        withInterveningIf(ControlYourCommanderCondition.instance);
    }

    private FightForTheThroneTriggeredAbility(final FightForTheThroneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FightForTheThroneTriggeredAbility copy() {
        return new FightForTheThroneTriggeredAbility(this);
    }

    @Override
    public void init(Game game) {
        // the creature an opponent controls is the second target, it's empty if that target became illegal
        mor = new MageObjectReference(getTargets().get(1).getFirstTarget(), game);
        getTargets().clear();
    }
}
