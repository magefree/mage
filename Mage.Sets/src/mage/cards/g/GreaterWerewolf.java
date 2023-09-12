
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class GreaterWerewolf extends CardImpl {

    public GreaterWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        
        // At end of combat, put a -0/-2 counter on each creature blocking or blocked by Greater Werewolf.
        this.addAbility(new EndOfCombatTriggeredAbility(new GreaterWerewolfEffect(), false));
    }

    private GreaterWerewolf(final GreaterWerewolf card) {
        super(card);
    }

    @Override
    public GreaterWerewolf copy() {
        return new GreaterWerewolf(this);
    }
}

class GreaterWerewolfEffect extends OneShotEffect {

    public GreaterWerewolfEffect() {
        super(Outcome.Detriment);
        this.staticText = "put a -0/-2 counter on each creature blocking or blocked by {this}";
    }

    private GreaterWerewolfEffect(final GreaterWerewolfEffect effect) {
        super(effect);
    }

    @Override
    public GreaterWerewolfEffect copy() {
        return new GreaterWerewolfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(Predicates.or(new BlockedByIdPredicate(sourcePermanent.getId()), new BlockingAttackerIdPredicate(sourcePermanent.getId())));
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                Effect effect = new AddCountersTargetEffect(CounterType.M0M2.createInstance(), Outcome.UnboostCreature);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
