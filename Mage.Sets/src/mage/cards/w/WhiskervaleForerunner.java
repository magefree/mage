package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ValiantTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhiskervaleForerunner extends CardImpl {

    public WhiskervaleForerunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Valiant -- Whenever Whiskervale Forerunner becomes the target of a spell or ability you control for the first time each turn, look at the top five cards of your library. You may reveal a creature card with mana value 3 or less from among them. You may put it onto the battlefield if it's your turn. If you don't put it onto the battlefield, put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new ValiantTriggeredAbility(new WhiskervaleForerunnerEffect()));
    }

    private WhiskervaleForerunner(final WhiskervaleForerunner card) {
        super(card);
    }

    @Override
    public WhiskervaleForerunner copy() {
        return new WhiskervaleForerunner(this);
    }
}

class WhiskervaleForerunnerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    WhiskervaleForerunnerEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top five cards of your library. You may reveal a creature card " +
                "with mana value 3 or less from among them. You may put it onto the battlefield " +
                "if it's your turn. If you don't put it onto the battlefield, put it into your hand. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private WhiskervaleForerunnerEffect(final WhiskervaleForerunnerEffect effect) {
        super(effect);
    }

    @Override
    public WhiskervaleForerunnerEffect copy() {
        return new WhiskervaleForerunnerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        TargetCard target = new TargetCardInLibrary(filter);
        player.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (game.isActivePlayer(player.getId())
                && player.chooseUse(Outcome.PutCreatureInPlay, "Put " + card.getName() + " onto the battlefield?", source, game)) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        if (game.getState().getZone(card.getId()) == Zone.LIBRARY) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
