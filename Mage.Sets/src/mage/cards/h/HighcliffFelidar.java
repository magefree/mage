package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HighcliffFelidar extends CardImpl {

    public HighcliffFelidar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Highcliff Felidar enters the battlefield, for each opponent, choose a creature with the greatest power among creatures that player controls. Destroy those creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HighcliffFelidarEffect()));
    }

    private HighcliffFelidar(final HighcliffFelidar card) {
        super(card);
    }

    @Override
    public HighcliffFelidar copy() {
        return new HighcliffFelidar(this);
    }
}

class HighcliffFelidarEffect extends OneShotEffect {

    HighcliffFelidarEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, choose a creature with the greatest power "
                + "among creatures that player controls. Destroy those creatures.";
    }

    private HighcliffFelidarEffect(final HighcliffFelidarEffect effect) {
        super(effect);
    }

    @Override
    public HighcliffFelidarEffect copy() {
        return new HighcliffFelidarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<UUID> toDestroy = new HashSet();
        game.getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEachOrdered(opponent -> {
                    int maxPower = game
                            .getBattlefield()
                            .getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game)
                            .stream()
                            .map(Permanent::getPower)
                            .mapToInt(MageInt::getValue)
                            .max()
                            .orElse(Integer.MIN_VALUE);
                    if (maxPower > Integer.MIN_VALUE) {
                        FilterPermanent filter = new FilterCreaturePermanent(
                                "creature with the greatest power controlled by " + opponent.getName()
                        );
                        filter.add(new ControllerIdPredicate(opponent.getId()));
                        filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, maxPower));
                        TargetPermanent target = new TargetPermanent(filter);
                        target.setNotTarget(true);
                        if (controller.choose(outcome, target, source, game)) {
                            toDestroy.add(target.getFirstTarget());
                        }
                    }
                });
        toDestroy.stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .forEachOrdered(permanent -> permanent.destroy(source, game, false));
        return true;
    }
}

// I realized after writing all this that the ability doesn't target but I like this code too much to erase it
//enum HighcliffFelidarAdjuster implements TargetAdjuster {
//    instance;
//
//    private static class HighcliffFelidarPredicate implements Predicate<Permanent> {
//        private final UUID controllerId;
//
//        private HighcliffFelidarPredicate(UUID controllerId) {
//            this.controllerId = controllerId;
//        }
//
//        @Override
//        public boolean apply(Permanent input, Game game) {
//            if (input == null || !input.isControlledBy(controllerId) || !input.isCreature()) {
//                return false;
//            }
//            int maxPower = game.getBattlefield()
//                    .getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controllerId, game)
//                    .stream()
//                    .map(Permanent::getPower)
//                    .mapToInt(MageInt::getValue)
//                    .max()
//                    .orElse(Integer.MIN_VALUE);
//            return input.getPower().getValue() >= maxPower;
//        }
//
//        private static TargetPermanent getTarget(Player player) {
//            FilterPermanent filter = new FilterPermanent("creature with the greatest power controlled by " + player.getName());
//            filter.add(new HighcliffFelidarPredicate(player.getId()));
//            return new TargetPermanent(filter);
//        }
//    }
//
//    @Override
//    public void adjustTargets(Ability ability, Game game) {
//        ability.getTargets().clear();
//        game.getOpponents(ability.getControllerId())
//                .stream()
//                .map(game::getPlayer)
//                .filter(Objects::nonNull)
//                .map(HighcliffFelidarPredicate::getTarget)
//                .forEachOrdered(ability::addTarget);
//    }
//}
