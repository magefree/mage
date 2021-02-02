
package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public final class SphinxOfUthuun extends CardImpl {

    public SphinxOfUthuun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SphinxOfUthuunEffect()));
    }

    private SphinxOfUthuun(final SphinxOfUthuun card) {
        super(card);
    }

    @Override
    public SphinxOfUthuun copy() {
        return new SphinxOfUthuun(this);
    }
}

class SphinxOfUthuunEffect extends OneShotEffect {

    public SphinxOfUthuunEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top five cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard";
    }

    public SphinxOfUthuunEffect(final SphinxOfUthuunEffect effect) {
        super(effect);
    }

    @Override
    public SphinxOfUthuunEffect copy() {
        return new SphinxOfUthuunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        controller.revealCards(source, cards, game);

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.isEmpty()) {
            Player opponent = game.getPlayer(opponents.iterator().next());
            if (opponents.size() > 1) {
                Target targetOpponent = new TargetOpponent(true);
                if (controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game)) {
                    opponent = game.getPlayer(targetOpponent.getFirstTarget());
                    game.informPlayers(controller.getLogName() + " chose " + opponent.getLogName() + " to separate the revealed cards");
                }
            }

            TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, new FilterCard("cards to put in the first pile"));
            target.setRequired(false);
            List<Card> pile1 = new ArrayList<>();
            Cards pile1CardsIds = new CardsImpl();
            if (opponent.choose(Outcome.Neutral, cards, target, game)) {
                pile1CardsIds.addAll(target.getTargets());
                pile1.addAll(pile1CardsIds.getCards(game));
            }
            List<Card> pile2 = new ArrayList<>();
            Cards pile2CardsIds = new CardsImpl(cards);
            pile2CardsIds.removeAll(pile1CardsIds);
            pile2.addAll(pile2CardsIds.getCards(game));

            boolean choice = controller.choosePile(Outcome.DestroyPermanent, "Choose a pile to put into hand.", pile1, pile2, game);

            Zone pile1Zone = Zone.GRAVEYARD;
            Zone pile2Zone = Zone.HAND;
            if (choice) {
                pile1Zone = Zone.HAND;
                pile2Zone = Zone.GRAVEYARD;
            }

            StringBuilder sb = new StringBuilder(sourceObject.getLogName()).append(": Pile 1, going to ").append(pile1Zone == Zone.HAND ? "Hand" : "Graveyard").append(": ");
            int i = 0;
            for (UUID cardUuid : pile1CardsIds) {
                i++;
                Card card = game.getCard(cardUuid);
                if (card != null) {
                    sb.append(card.getName());
                    if (i < pile1CardsIds.size()) {
                        sb.append(", ");
                    }
                }
            }
            controller.moveCards(pile1CardsIds, pile1Zone, source, game);
            game.informPlayers(sb.toString());

            sb = new StringBuilder(sourceObject.getLogName()).append(": Pile 2, going to ").append(pile2Zone == Zone.HAND ? "Hand" : "Graveyard").append(':');
            i = 0;
            for (UUID cardUuid : pile2CardsIds) {
                Card card = game.getCard(cardUuid);
                if (card != null) {
                    i++;
                    sb.append(' ').append(card.getName());
                    if (i < pile2CardsIds.size()) {
                        sb.append(", ");
                    }
                }
            }
            controller.moveCards(pile2CardsIds, pile2Zone, source, game);
            game.informPlayers(sb.toString());
        }

        return true;
    }
}
