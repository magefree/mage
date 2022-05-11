
package mage.cards.a;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class Abundance extends CardImpl {

    public Abundance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // If you would draw a card, you may instead choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbundanceReplacementEffect()));
    }

    private Abundance(final Abundance card) {
        super(card);
    }

    @Override
    public Abundance copy() {
        return new Abundance(this);
    }
}

class AbundanceReplacementEffect extends ReplacementEffectImpl {

    AbundanceReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, you may instead choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order";
    }

    AbundanceReplacementEffect(final AbundanceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AbundanceReplacementEffect copy() {
        return new AbundanceReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            FilterCard filter = new FilterCard();
            if (controller.chooseUse(Outcome.Detriment, "Choose card type:",
                    source.getSourceObject(game).getLogName(), "land", "nonland", source, game)) {
                game.informPlayers(controller.getLogName() + "chooses land.");
                filter.add(CardType.LAND.getPredicate());
            } else {
                game.informPlayers(controller.getLogName() + "chooses nonland.");
                filter.add(Predicates.not(CardType.LAND.getPredicate()));
            }
            Cards toReveal = new CardsImpl();
            Card selectedCard = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                if (filter.match(card, source.getControllerId(), source, game)) {
                    selectedCard = card;
                    break;
                }

            }
            controller.moveCards(selectedCard, Zone.HAND, source, game);
            controller.revealCards(sourceObject.getIdName(), toReveal, game);
            toReveal.remove(selectedCard);
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, true);

        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                return player.chooseUse(Outcome.Detriment, "Choose:", source.getSourceObject(game).getLogName(),
                        "land or nonland and reveal cards from the top", "normal card draw", source, game);
            }
        }
        return false;
    }
}
