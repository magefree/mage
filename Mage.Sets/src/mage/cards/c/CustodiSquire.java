
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CustodiSquire extends CardImpl {

    public CustodiSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Will of the council - When Custodi Squire enters the battlefield, starting with you, each player votes for an artifact, creature, or enchantment card in your graveyard. Return each card with the most votes or tied for most votes to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CustodiSquireVoteEffect(), false, true));
    }

    public CustodiSquire(final CustodiSquire card) {
        super(card);
    }

    @Override
    public CustodiSquire copy() {
        return new CustodiSquire(this);
    }
}

class CustodiSquireVoteEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("artifact, creature, or enchantment card from your graveyard");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    CustodiSquireVoteEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; When {this} enters the battlefield, starting with you, each player votes for an artifact, creature, or enchantment card in your graveyard. Return each card with the most votes or tied for most votes to your hand";
    }

    CustodiSquireVoteEffect(final CustodiSquireVoteEffect effect) {
        super(effect);
    }

    @Override
    public CustodiSquireVoteEffect copy() {
        return new CustodiSquireVoteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards possibleCards = new CardsImpl();
            possibleCards.addAll(controller.getGraveyard().getCards(filter, game));
            if (!possibleCards.isEmpty()) {
                Map<UUID, Integer> cardCounter = new HashMap<>();
                TargetCard target = new TargetCard(1, 1, Zone.GRAVEYARD, filter);
                int maxCount = 1;
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        target.clearChosen();
                        player.chooseTarget(outcome, possibleCards, target, source, game);
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            game.informPlayers(player.getName() + " voted for " + card.getLogName());
                            if (!cardCounter.containsKey(target.getFirstTarget())) {
                                cardCounter.put(target.getFirstTarget(), 1);
                            } else {
                                int count = cardCounter.get(target.getFirstTarget()) + 1;
                                if (count > maxCount) {
                                    maxCount = count;
                                }
                                cardCounter.put(target.getFirstTarget(), count);
                            }
                        }
                    }
                }
                Cards cardsToMove = new CardsImpl();
                for (UUID uuid : possibleCards) {
                    if (cardCounter.containsKey(uuid)) {
                        if (cardCounter.get(uuid) == maxCount) {
                            cardsToMove.add(uuid);
                        }
                    }
                }
                controller.moveCards(cardsToMove, Zone.HAND, source, game);
            }
            return true;
        }
        return false;

    }
}
