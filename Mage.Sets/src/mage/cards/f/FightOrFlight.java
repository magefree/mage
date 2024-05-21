package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author LevelX2 & L_J
 */
public final class FightOrFlight extends CardImpl {

    public FightOrFlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // At the beginning of combat on each opponent’s turn, separate all creatures that player controls into two piles. Only creatures in the pile of their choice can attack this turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new FightOrFlightEffect(), TargetController.OPPONENT, false));
    }

    private FightOrFlight(final FightOrFlight card) {
        super(card);
    }

    @Override
    public FightOrFlight copy() {
        return new FightOrFlight(this);
    }
}

class FightOrFlightEffect extends OneShotEffect {

    FightOrFlightEffect() {
        super(Outcome.Detriment);
        this.staticText = "separate all creatures that player controls into two piles. Only creatures in the pile of their choice can attack this turn";
    }

    private FightOrFlightEffect(final FightOrFlightEffect effect) {
        super(effect);
    }

    @Override
    public FightOrFlightEffect copy() {
        return new FightOrFlightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(game.getCombat().getAttackingPlayerId());
        if (player == null || targetPlayer == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures to put in the first pile");
        filter.add(new ControllerIdPredicate(targetPlayer.getId()));
        TargetCreaturePermanent creatures = new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
        List<Permanent> pile1 = new ArrayList<>();
        creatures.setRequired(false);
        if (player.choose(Outcome.Neutral, creatures, source, game)) {
            List<UUID> targets = creatures.getTargets();
            for (UUID targetId : targets) {
                Permanent p = game.getPermanent(targetId);
                if (p != null) {
                    pile1.add(p);
                }
            }
        }
        List<Permanent> pile2 = new ArrayList<>();
        for (Permanent p : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game)) {
            if (!pile1.contains(p)) {
                pile2.add(p);
            }
        }

        boolean choice = targetPlayer.choosePile(outcome, "Choose which pile can attack this turn.", pile1, pile2, game);
        List<Permanent> canAttack = choice ? pile1 : pile2;
        FilterCreaturePermanent filterRestriction = new FilterCreaturePermanent();
        filterRestriction.add(Predicates.not(new PermanentReferenceInCollectionPredicate(canAttack, game)));
        game.addEffect(new CantAttackAllEffect(Duration.EndOfTurn, filterRestriction), source);
        game.informPlayers("Creatures that can attack this turn: " + canAttack
                .stream()
                .map(Permanent::getLogName)
                .collect(Collectors.joining(", ")));
        return true;
    }
}
