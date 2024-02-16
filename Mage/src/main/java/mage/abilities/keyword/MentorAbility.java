package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

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
        super(new MentorEffect(), false);
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

class MentorEffect extends AddCountersTargetEffect {

    MentorEffect() {
        super(CounterType.P1P1.createInstance(), Outcome.BoostCreature);
    }

    private MentorEffect(final MentorEffect effect) {
        super(effect);
    }

    @Override
    public MentorEffect copy() {
        return new MentorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!super.apply(game, source)) {
            return false;
        }

        Permanent mentoredPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (mentoredPermanent == null) {
            return false;
        }
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.MENTORED_CREATURE,
                mentoredPermanent.getId(),
                source,
                source.getControllerId()));
        return true;
    }
}
