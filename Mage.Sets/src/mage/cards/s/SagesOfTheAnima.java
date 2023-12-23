package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class SagesOfTheAnima extends CardImpl {

    public SagesOfTheAnima(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If you would draw a card, instead reveal the top three cards of your library. Put all creature cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SagesOfTheAnimaReplacementEffect()));

    }

    private SagesOfTheAnima(final SagesOfTheAnima card) {
        super(card);
    }

    @Override
    public SagesOfTheAnima copy() {
        return new SagesOfTheAnima(this);
    }
}

class SagesOfTheAnimaReplacementEffect extends ReplacementEffectImpl {

    public SagesOfTheAnimaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, instead "
                + "reveal the top three cards of your library. "
                + "Put all creature cards revealed this way into your hand "
                + "and the rest on the bottom of your library in any order";
    }

    private SagesOfTheAnimaReplacementEffect(final SagesOfTheAnimaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SagesOfTheAnimaReplacementEffect copy() {
        return new SagesOfTheAnimaReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            Cards revealedCards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
            player.revealCards(source, revealedCards, game);
            Cards creatures = new CardsImpl(revealedCards.getCards(StaticFilters.FILTER_CARD_CREATURE, game));
            player.moveCards(creatures, Zone.HAND, source, game);
            revealedCards.removeAll(creatures);
            player.putCardsOnBottomOfLibrary(revealedCards, game, source, true);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
