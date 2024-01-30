package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public class MentorAbility extends AttacksTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature with lesser power");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(MentorAbilityPredicate.instance);
    }

    public MentorAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance(), Outcome.BoostCreature), false);
        addWatcher(new MentoredWatcher());
        this.addTarget(new TargetCreaturePermanent(filter));
    }

    protected MentorAbility(final MentorAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "mentor <i>(Whenever this creature attacks, put a +1/+1 counter on target attacking creature with lesser power.)</i>";
    }

    @Override
    public MentorAbility copy() {
        return new MentorAbility(this);
    }

}

enum MentorAbilityPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null && input.getObject().getPower().getValue() < sourcePermanent.getPower().getValue();
    }
}

class MentoredWatcher extends Watcher {

    public MentoredWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // TODO Make sure this is correct after the comprehensive rules update for CLU/MKM, add citation
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED && event.getData().equals(CounterType.P1P1.getName())) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (stackAbility == null) {
                return;
            }

            Ability ability = stackAbility.getStackAbility();
            if (ability instanceof MentorAbility) {
                game.fireEvent(GameEvent.getEvent(
                        GameEvent.EventType.MENTORED_CREATURE,
                        event.getTargetId(),
                        ability,
                        event.getPlayerId()));
            }
        }
    }
}
