package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.SummoningSicknessPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public class EnlistAbility extends StaticAbility {

    public EnlistAbility() {
        super(Zone.BATTLEFIELD, new EnlistEffect());
    }

    private EnlistAbility(final EnlistAbility ability) {
        super(ability);
    }

    @Override
    public EnlistAbility copy() {
        return new EnlistAbility(this);
    }

    @Override
    public String getRule() {
        return "Enlist <i>(As this creature attacks, you may tap a nonattacking creature you control " +
                "without summoning sickness. When you do, add its power to this creature's until end of turn.)</i>";
    }
}

class EnlistEffect extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(
            "another untapped nonattacking creature you control without summoning sickness"
    );

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.not(SummoningSicknessPredicate.instance));
        filter.add(Predicates.not(AttackingPredicate.instance));
    }

    public EnlistEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
    }

    public EnlistEffect(EnlistEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = game.getPermanent(event.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (creature == null || controller == null
                || !game.getBattlefield().contains(filter, source, game, 1)
                || !controller.chooseUse(outcome, "Enlist a creature for " + creature.getLogName() + '?', source, game)) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        controller.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.tap(source, game)) {
            return false;
        }
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new BoostSourceEffect(
                        permanent.getPower().getValue(),
                        0, Duration.EndOfTurn
                ), false
        ), source);
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.CREATURE_ENLISTED,
                permanent.getId(), source, source.getControllerId()
        ));
        return false;
    }

    @Override
    public EnlistEffect copy() {
        return new EnlistEffect(this);
    }
}
