package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
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
 * @author TheElk801
 */
public final class MagusOfTheBalance extends CardImpl {

    public MagusOfTheBalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{W}, {T}, Sacrifice Magus of the Balance: Each player chooses a number of lands they control equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players discard cards and sacrifice creatures the same way.
        Ability ability = new SimpleActivatedAbility(
                new MagusOfTheBalanceEffect(),
                new ManaCostsImpl("{4}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public MagusOfTheBalance(final MagusOfTheBalance card) {
        super(card);
    }

    @Override
    public MagusOfTheBalance copy() {
        return new MagusOfTheBalance(this);
    }
}

class MagusOfTheBalanceEffect extends OneShotEffect {

    MagusOfTheBalanceEffect() {
        super(Outcome.Sacrifice);
        staticText = "each player chooses a number of lands they control "
                + "equal to the number of lands controlled by the player "
                + "who controls the fewest, then sacrifices the rest. "
                + "Players discard cards and sacrifice creatures the same way";
    }

    MagusOfTheBalanceEffect(final MagusOfTheBalanceEffect effect) {
        super(effect);
    }

    @Override
    public MagusOfTheBalanceEffect copy() {
        return new MagusOfTheBalanceEffect(this);
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
                    TargetControlledPermanent target = new TargetControlledPermanent(minLand, minLand, new FilterControlledLandPermanent("lands to keep"), true);
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
                    TargetControlledPermanent target = new TargetControlledPermanent(minCreature, minCreature, new FilterControlledCreaturePermanent("creatures to keep"), true);
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
                    TargetCardInHand target = new TargetCardInHand(minCard, new FilterCard("cards to keep"));
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
