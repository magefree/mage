package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EruthTormentedProphet extends CardImpl {

    public EruthTormentedProphet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // If you would draw a card, exile the top two cards of your library instead. You may play those cards this turn.
        this.addAbility(new SimpleStaticAbility(new EruthTormentedProphetEffect()));
    }

    private EruthTormentedProphet(final EruthTormentedProphet card) {
        super(card);
    }

    @Override
    public EruthTormentedProphet copy() {
        return new EruthTormentedProphet(this);
    }
}

class EruthTormentedProphetEffect extends ReplacementEffectImpl {

    EruthTormentedProphetEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would draw a card, exile the top two cards " +
                "of your library instead. You may play those cards this turn";
    }

    private EruthTormentedProphetEffect(final EruthTormentedProphetEffect effect) {
        super(effect);
    }

    @Override
    public EruthTormentedProphetEffect copy() {
        return new EruthTormentedProphetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null) {
            return true;
        }
        Set<Card> cards = player.getLibrary().getTopCards(game, 2);
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}
