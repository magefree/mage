package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

import java.util.*;

/**
 * @author Plopman (Restore Balance), cbt33
 */
public final class BalancingAct extends CardImpl {

    public BalancingAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Each player chooses a number of permanents they control equal to the number of permanents controlled by the player who controls the fewest, then sacrifices the rest.
        this.getSpellAbility().addEffect(new BalancingActSacrificeEffect());
        // Each player discards cards the same way.
        this.getSpellAbility().addEffect(new BalancingActDiscardEffect());
    }

    private BalancingAct(final BalancingAct card) {
        super(card);
    }

    @Override
    public BalancingAct copy() {
        return new BalancingAct(this);
    }
}

class BalancingActSacrificeEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("permanents to keep");

    BalancingActSacrificeEffect() {
        super(Outcome.Sacrifice);
        staticText = "Each player chooses a number of permanents they control equal to the number of permanents controlled by the player who controls the fewest, then sacrifices the rest.";
    }

    private BalancingActSacrificeEffect(final BalancingActSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public BalancingActSacrificeEffect copy() {
        return new BalancingActSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // count minimal permanents
        int minPermanent = Integer.MAX_VALUE;
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                minPermanent = Math.min(minPermanent, game.getBattlefield().countAll(filter, player.getId(), game));
            }
        }

        // each player chooses permanents to keep (if the minimum is 0 nothing can be kept)
        List<Permanent> toSacrifice = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, player.getId(), source, game);
            Set<UUID> toKeep = new HashSet<>();
            if (minPermanent > 0) {
                TargetControlledPermanent target = new TargetControlledPermanent(minPermanent, minPermanent, filter, true);
                target.choose(Outcome.Protect, player.getId(), source.getSourceId(), source, game);
                toKeep.addAll(target.getTargets());
                // prevent possible cheat by disconnecting/cancelling: keep the first permanents in the list
                for (Permanent permanent : permanents) {
                    if (toKeep.size() >= minPermanent) {
                        break;
                    }
                    toKeep.add(permanent.getId());
                }
            }
            for (Permanent permanent : permanents) {
                if (!toKeep.contains(permanent.getId())) {
                    toSacrifice.add(permanent);
                }
            }
        }

        // then all players sacrifice the rest simultaneously
        for (Permanent permanent : toSacrifice) {
            permanent.sacrifice(source, game);
        }
        return true;
    }
}

class BalancingActDiscardEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("cards to keep");

    BalancingActDiscardEffect() {
        super(Outcome.Discard);
        staticText = "Each player discards cards the same way";
    }

    private BalancingActDiscardEffect(final BalancingActDiscardEffect effect) {
        super(effect);
    }

    @Override
    public BalancingActDiscardEffect copy() {
        return new BalancingActDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // count minimal cards in hand
        int minCard = Integer.MAX_VALUE;
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                minCard = Math.min(minCard, player.getHand().size());
            }
        }

        // each player chooses cards to keep (if the minimum is 0 nothing can be kept)
        Map<UUID, Cards> toDiscard = new LinkedHashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Set<UUID> toKeep = new HashSet<>();
            if (minCard > 0) {
                TargetCardInHand target = new TargetCardInHand(minCard, minCard, filter);
                target.choose(Outcome.Protect, player.getId(), source.getSourceId(), source, game);
                toKeep.addAll(target.getTargets());
                // prevent possible cheat by disconnecting/cancelling: keep the first cards in the list
                for (UUID cardId : player.getHand()) {
                    if (toKeep.size() >= minCard) {
                        break;
                    }
                    toKeep.add(cardId);
                }
            }
            Cards cards = player.getHand().copy();
            cards.removeIf(toKeep::contains);
            toDiscard.put(playerId, cards);
        }

        // then all players discard the rest
        for (Map.Entry<UUID, Cards> entry : toDiscard.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player != null && !entry.getValue().isEmpty()) {
                player.discard(entry.getValue(), false, source, game);
            }
        }
        return true;
    }
}
