
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class RestoreBalance extends CardImpl {

    public RestoreBalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setWhite(true);

        // Suspend 6-{W}
        this.addAbility(new SuspendAbility(6, new ColoredManaCost(ColoredManaSymbol.W), this));
        // Each player chooses a number of lands he or she controls equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players sacrifice creatures and discard cards the same way.
        this.getSpellAbility().addEffect(new RestoreBalanceEffect());
    }

    public RestoreBalance(final RestoreBalance card) {
        super(card);
    }

    @Override
    public RestoreBalance copy() {
        return new RestoreBalance(this);
    }
}


class RestoreBalanceEffect extends OneShotEffect {

    RestoreBalanceEffect() {
        super(Outcome.Sacrifice);
        staticText = "Each player chooses a number of lands he or she controls equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players sacrifice creatures and discard cards the same way";
    }

    RestoreBalanceEffect(final RestoreBalanceEffect effect) {
        super(effect);
    }

    @Override
    public RestoreBalanceEffect copy() {
        return new RestoreBalanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            //Lands
            int minLand = Integer.MAX_VALUE;
            Cards landsToSacrifice = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = game.getBattlefield().countAll(new FilterControlledLandPermanent(), player.getId(), game);
                    if (count < minLand) {
                        minLand = count;
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetControlledPermanent target = new TargetControlledPermanent(minLand, minLand, new FilterControlledLandPermanent(), true);
                    if (target.choose(Outcome.Sacrifice, player.getId(), source.getSourceId(), game)) {
                        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), player.getId(), source.getSourceId(), game)) {
                            if (permanent != null && !target.getTargets().contains(permanent.getId())) {
                                landsToSacrifice.add(permanent);
                            }
                        }
                    }
                }
            }

            for (UUID cardId : landsToSacrifice) {
                Permanent permanent = game.getPermanent(cardId);
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }

            //Creatures
            int minCreature = Integer.MAX_VALUE;
            Cards creaturesToSacrifice = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), player.getId(), game);
                    if (count < minCreature) {
                        minCreature = count;
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetControlledPermanent target = new TargetControlledPermanent(minCreature, minCreature, new FilterControlledCreaturePermanent(), true);
                    if (target.choose(Outcome.Sacrifice, player.getId(), source.getSourceId(), game)) {
                        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), player.getId(), source.getSourceId(), game)) {
                            if (permanent != null && !target.getTargets().contains(permanent.getId())) {
                                creaturesToSacrifice.add(permanent);
                            }
                        }
                    }
                }
            }

            for (UUID cardId : creaturesToSacrifice) {
                Permanent permanent = game.getPermanent(cardId);
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }

            //Cards in hand
            int minCard = Integer.MAX_VALUE;
            Map<UUID, Cards> cardsToDiscard = new HashMap<>(2);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = player.getHand().size();
                    if (count < minCard) {
                        minCard = count;
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cards = new CardsImpl();
                    TargetCardInHand target = new TargetCardInHand(minCard, new FilterCard());
                    if (target.choose(Outcome.Discard, player.getId(), source.getSourceId(), game)) {
                        for (Card card : player.getHand().getCards(game)) {
                            if (card != null && !target.getTargets().contains(card.getId())) {
                                cards.add(card);
                            }
                        }
                        cardsToDiscard.put(playerId, cards);
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && cardsToDiscard.get(playerId) != null) {
                    for (UUID cardId : cardsToDiscard.get(playerId)) {
                        Card card = game.getCard(cardId);
                        player.discard(card, source, game);

                    }
                }
            }
            return true;
        }
        return false;
    }
}
