package mage.cards.u;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class UneshCriosphinxSovereign extends CardImpl {

    private static final FilterCard filter = new FilterCard("Sphinx spells");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.SPHINX, "Sphinx");

    static {
        filter.add(SubType.SPHINX.getPredicate());
    }

    public UneshCriosphinxSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sphinx spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2)));

        // Whenever Unesh, Criosphinx Sovereign or another Sphinx enters the battlefield under your control, reveal the top four cards of your library. An opponent seperates those cards into two piles. Put one pile into your hand and the other into your graveyard.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new UneshCriosphinxSovereignEffect(), filter2, false, true
        ));
    }

    private UneshCriosphinxSovereign(final UneshCriosphinxSovereign card) {
        super(card);
    }

    @Override
    public UneshCriosphinxSovereign copy() {
        return new UneshCriosphinxSovereign(this);
    }
}

class UneshCriosphinxSovereignEffect extends OneShotEffect {

    UneshCriosphinxSovereignEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard";
    }

    private UneshCriosphinxSovereignEffect(final UneshCriosphinxSovereignEffect effect) {
        super(effect);
    }

    @Override
    public UneshCriosphinxSovereignEffect copy() {
        return new UneshCriosphinxSovereignEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> cardsToGraveyard = new LinkedHashSet<>();
        Set<Card> cardsToHand = new LinkedHashSet<>();
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        controller.revealCards(sourceObject.getName(), cards, game);

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
            List<Card> pile1 = new ArrayList<>();
            if (opponent.choose(Outcome.Neutral, cards, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = cards.get(targetId, game);
                    if (card != null) {
                        pile1.add(card);
                        cards.remove(card);
                    }
                }
            }
            List<Card> pile2 = new ArrayList<>();
            pile2.addAll(cards.getCards(game));

            boolean choice = controller.choosePile(outcome, "Choose a pile to put into your hand.", pile1, pile2, game);

            Zone pile1Zone = Zone.GRAVEYARD;
            Zone pile2Zone = Zone.HAND;
            if (choice) {
                pile1Zone = Zone.HAND;
                pile2Zone = Zone.GRAVEYARD;
            }

            StringBuilder sb = new StringBuilder("Pile 1, going to ").append(pile1Zone == Zone.HAND ? "Hand" : "Graveyard").append(": ");
            int i = 0;
            for (Card card : pile1) {
                i++;
                sb.append(card.getName());
                if (i < pile1.size()) {
                    sb.append(", ");
                }
                cardsToGraveyard.add(card);
            }
            controller.moveCards(cardsToGraveyard, pile1Zone, source, game);
            game.informPlayers(sb.toString());

            sb = new StringBuilder("Pile 2, going to ").append(pile2Zone == Zone.HAND ? "Hand" : "Graveyard").append(':');
            i = 0;
            for (Card card : pile2) {
                i++;
                sb.append(' ').append(card.getName());
                if (i < pile2.size()) {
                    sb.append(", ");
                }
                cardsToHand.add(card);
            }
            controller.moveCards(cardsToHand, pile2Zone, source, game);
            game.informPlayers(sb.toString());
        }

        return true;
    }
}
