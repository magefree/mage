package mage.cards.g;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class GuidedPassage extends CardImpl {

    public GuidedPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{U}{R}");

        // Reveal the cards in your library. An opponent chooses from among them a creature card, a land card, and a noncreature, nonland card. You put the chosen cards into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new GuidedPassageEffect());
    }

    private GuidedPassage(final GuidedPassage card) {
        super(card);
    }

    @Override
    public GuidedPassage copy() {
        return new GuidedPassage(this);
    }
}

class GuidedPassageEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    GuidedPassageEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the cards in your library. An opponent chooses from among them a creature card, a land card, and a noncreature, nonland card. You put the chosen cards into your hand. Then shuffle.";
    }

    GuidedPassageEffect(final GuidedPassageEffect effect) {
        super(effect);
    }

    @Override
    public GuidedPassageEffect copy() {
        return new GuidedPassageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        int libSize = controller.getLibrary().size();
        if (libSize == 0) {
            controller.shuffleLibrary(source, game);
            return true;
        }
        CardsImpl cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, libSize));
        controller.revealCards(sourceObject.getIdName(), cards, game);

        Player opponent;
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            Target target = new TargetOpponent(true);
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            opponent = game.getPlayer(target.getFirstTarget());
        }
        TargetCard target1 = new TargetCard(1, Zone.LIBRARY, StaticFilters.FILTER_CARD_CREATURE);
        TargetCard target2 = new TargetCard(1, Zone.LIBRARY, StaticFilters.FILTER_CARD_LAND);
        TargetCard target3 = new TargetCard(1, Zone.LIBRARY, new FilterCard(filter));
        opponent.chooseTarget(Outcome.Detriment, cards, target1, source, game);
        opponent.chooseTarget(Outcome.Detriment, cards, target2, source, game);
        opponent.chooseTarget(Outcome.Detriment, cards, target3, source, game);
        Cards cardsToHand = new CardsImpl();
        Card cardToHand;
        cardToHand = game.getCard(target1.getFirstTarget());
        if (cardToHand != null) {
            cardsToHand.add(cardToHand);
        }
        cardToHand = game.getCard(target2.getFirstTarget());
        if (cardToHand != null) {
            cardsToHand.add(cardToHand);
        }
        cardToHand = game.getCard(target3.getFirstTarget());
        if (cardToHand != null) {
            cardsToHand.add(cardToHand);
        }
        controller.moveCards(cardsToHand, Zone.HAND, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
