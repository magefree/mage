package mage.cards.c;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CollectedConjuring extends CardImpl {

    public CollectedConjuring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{R}");

        // Exile the top six cards of your library. You may cast up to two sorcery cards with converted mana costs 3 or less from among them without paying their mana cost. Put the exiled cards not cast this way on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new CollectedConjuringEffect());
    }

    private CollectedConjuring(final CollectedConjuring card) {
        super(card);
    }

    @Override
    public CollectedConjuring copy() {
        return new CollectedConjuring(this);
    }
}

class CollectedConjuringEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("sorcery cards with converted mana cost 3 or less");

    static {
        filter.add(new CardTypePredicate(CardType.SORCERY));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }

    private static final FilterCard filter2 = filter.copy();

    static {
        filter2.setMessage("sorcery card with converted mana cost 3 or less");
    }

    CollectedConjuringEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top six cards of your library. " +
                "You may cast up to two sorcery cards with converted mana costs 3 or less from among them " +
                "without paying their mana cost. Put the exiled cards not cast this way " +
                "on the bottom of your library in a random order.";
    }

    private CollectedConjuringEffect(final CollectedConjuringEffect effect) {
        super(effect);
    }

    @Override
    public CollectedConjuringEffect copy() {
        return new CollectedConjuringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 6));
        controller.moveCards(cards, Zone.EXILED, source, game);
        int cardsCast = 0;
        while (!cards.getCards(filter, source.getSourceId(), source.getControllerId(), game).isEmpty() && cardsCast < 2) {
            if (!controller.chooseUse(Outcome.PlayForFree, "Cast a card exiled with " + sourceObject.getLogName() + " without paying its mana cost?", source, game)) {
                break;
            }
            TargetCard targetCard = new TargetCard(1, Zone.EXILED, filter2);
            if (!controller.choose(Outcome.PlayForFree, cards, targetCard, source, game)) {
                continue;
            }
            Card card = game.getCard(targetCard.getFirstTarget());
            if (card == null) {
                continue;
            }
            if (controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game))) {
                cards.remove(card);
                cardsCast++;
            } else {
                game.informPlayer(controller, "You're not able to cast " + card.getIdName() + " or you canceled the casting.");
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
