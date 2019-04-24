package mage.abilities.keyword;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class MentorAbility extends AttacksTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature with lesser power");

    static {
        filter.add(new AttackingPredicate());
        filter.add(new MentorAbilityPredicate());
    }

    public MentorAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        this.addTarget(new TargetCreaturePermanent(filter));
    }

    public MentorAbility(final MentorAbility ability) {
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

class MentorAbilityPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Card>> {

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(input.getSourceId());
        return sourcePermanent != null && input.getObject().getPower().getValue() < sourcePermanent.getPower().getValue();
    }
}
