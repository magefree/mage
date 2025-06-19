package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.RandomUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class HaphazardBombardment extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("if two or more permanents you don't control have an aim counter on them");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(CounterType.AIM.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public HaphazardBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        // When Haphazard Bombardment enters the battlefield, choose four nonenchantment permanents you don't control and put an aim counter on each of them.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HaphazardBombardmentEffect(), false));

        // At the beginning of your end step, if two or more permanents you don't control have an aim counter on them, destroy one of those permanents at random.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new HaphazardBombardmentEndOfTurnEffect()).withInterveningIf(condition));
    }

    private HaphazardBombardment(final HaphazardBombardment card) {
        super(card);
    }

    @Override
    public HaphazardBombardment copy() {
        return new HaphazardBombardment(this);
    }
}

class HaphazardBombardmentEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("nonenchantment permanents you don't control");

    static {
        filter.add(Predicates.not(CardType.ENCHANTMENT.getPredicate()));
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    HaphazardBombardmentEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose four nonenchantment permanents you don't control and put an aim counter on each of them";
    }

    private HaphazardBombardmentEffect(final HaphazardBombardmentEffect effect) {
        super(effect);
    }

    @Override
    public HaphazardBombardmentEffect copy() {
        return new HaphazardBombardmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        if (permanents.size() > 4) {
            permanents.clear();
            TargetPermanent target = new TargetPermanent(4, 4, filter, true);
            controller.chooseTarget(outcome, target, source, game);
            permanents.addAll(
                    target.getTargets()
                            .stream()
                            .map(game::getPermanent)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
            );
        }
        for (Permanent permanent : permanents) {
            permanent.addCounters(CounterType.AIM.createInstance(), source.getControllerId(), source, game);
        }
        return true;

    }
}

class HaphazardBombardmentEndOfTurnEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(CounterType.AIM.getPredicate());
        // 4/27/2018 	If one or more of the permanents with aim counters on them have indestructible,
        // select the permanent destroyed at random from among the permanents with aim counters
        // that don't have indestructible.
        filter.add(Predicates.not(new AbilityPredicate(IndestructibleAbility.class)));
    }

    HaphazardBombardmentEndOfTurnEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy one of those permanents at random";
    }

    private HaphazardBombardmentEndOfTurnEffect(final HaphazardBombardmentEndOfTurnEffect effect) {
        super(effect);
    }

    @Override
    public HaphazardBombardmentEndOfTurnEffect copy() {
        return new HaphazardBombardmentEndOfTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.ofNullable(
                        game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)
                )
                .filter(p -> !p.isEmpty())
                .map(RandomUtil::randomFromCollection)
                .filter(permanent -> permanent.destroy(source, game))
                .isPresent();
    }
}
