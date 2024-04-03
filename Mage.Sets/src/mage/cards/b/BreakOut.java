package mage.cards.b;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author DominionSpy
 */
public final class BreakOut extends CardImpl {

    public BreakOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{G}");

        // Look at the top six cards of your library. You may reveal a creature card from among them.
        // If that card has mana value 2 or less, you may put it onto the battlefield and it gains haste until end of turn.
        // If you didn't put the revealed card onto the battlefield this way, put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new BreakOutEffect());
    }

    private BreakOut(final BreakOut card) {
        super(card);
    }

    @Override
    public BreakOut copy() {
        return new BreakOut(this);
    }
}

class BreakOutEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard();

    BreakOutEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top six cards of your library. You may reveal a creature card from among them. " +
                "If that card has mana value 2 or less, you may put it onto the battlefield and it gains haste until end of turn. " +
                "If you didn't put the revealed card onto the battlefield this way, put it into your hand. " +
                "Put the rest on the bottom of your library in a random order.";
    }

    private BreakOutEffect(final BreakOutEffect effect) {
        super(effect);
    }

    @Override
    public BreakOutEffect copy() {
        return new BreakOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 6));
        controller.lookAtCards(source, null, cards, game);

        if (cards.count(filter, source.getControllerId(), source, game) == 0 ||
                !controller.chooseUse(outcome, "Reveal a creature card?", source, game)) {
            return PutCards.BOTTOM_RANDOM.moveCards(controller, cards, source, game);
        }

        TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, filter);
        if (!controller.chooseTarget(outcome, cards, target, source, game)) {
            return PutCards.BOTTOM_RANDOM.moveCards(controller, cards, source, game);
        }

        Cards pickedCards = new CardsImpl(target.getTargets());
        controller.revealCards(source, pickedCards, game);
        cards.removeAll(pickedCards);

        Cards battlefieldCards = new CardsImpl(pickedCards
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> card.getManaValue() <= 2)
                .map(Card::getId)
                .collect(Collectors.toList()));
        pickedCards.removeAll(battlefieldCards);

        boolean result = PutCards.BATTLEFIELD.moveCards(controller, battlefieldCards, source, game);
        result |= PutCards.HAND.moveCards(controller, pickedCards, source, game);
        result |= PutCards.BOTTOM_RANDOM.moveCards(controller, cards, source, game);

        return result;
    }
}
