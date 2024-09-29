package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
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
 * @author xenohedron
 */
public final class StandOrFall extends CardImpl {

    public StandOrFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // At the beginning of combat on your turn, for each defending player, separate all creatures that player controls into two piles and that player chooses one. Only creatures in the chosen piles can block this turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new StandOrFallEffect(), TargetController.YOU, false));
    }

    private StandOrFall(final StandOrFall card) {
        super(card);
    }

    @Override
    public StandOrFall copy() {
        return new StandOrFall(this);
    }
}

class StandOrFallEffect extends OneShotEffect {

    StandOrFallEffect() {
        super(Outcome.Detriment);
        this.staticText = "for each defending player, separate all creatures that player controls into two piles and that player chooses one." +
                " Only creatures in the chosen piles can block this turn";
    }

    private StandOrFallEffect(final StandOrFallEffect effect) {
        super(effect);
    }

    @Override
    public StandOrFallEffect copy() {
        return new StandOrFallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Permanent> canBlock = new ArrayList<>();
        for (UUID oppId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(oppId);
            if (opponent == null) {
                continue;
            }
            FilterCreaturePermanent opponentFilter = new FilterCreaturePermanent();
            opponentFilter.add(new ControllerIdPredicate(oppId));
            TargetCreaturePermanent creatures = new TargetCreaturePermanent(0, Integer.MAX_VALUE, opponentFilter, true);
            creatures.setRequired(false);
            List<Permanent> pile1 = new ArrayList<>();
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
            for (Permanent p : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game)) {
                if (!pile1.contains(p)) {
                    pile2.add(p);
                }
            }
            boolean choice = opponent.choosePile(outcome, "Choose which pile can block this turn.", pile1, pile2, game);
            canBlock.addAll(choice ? pile1 : pile2);
        }
        FilterCreaturePermanent filterRestriction = new FilterCreaturePermanent();
        filterRestriction.add(Predicates.not(new PermanentReferenceInCollectionPredicate(canBlock, game)));
        game.addEffect(new CantBlockAllEffect(filterRestriction, Duration.EndOfTurn), source);
        game.informPlayers("Creatures that can block this turn: " + canBlock
                .stream()
                .map(Permanent::getLogName)
                .collect(Collectors.joining(", ")));
        return true;
    }
}
