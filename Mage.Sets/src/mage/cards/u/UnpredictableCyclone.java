package mage.cards.u;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnpredictableCyclone extends CardImpl {

    public UnpredictableCyclone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // If a cycling ability of another nonland card would cause you to draw a card, instead exile cards from the top of your library until you exile a card that shares a card type with the cycled card. You may cast that card without paying its mana cost. Then put the exiled cards that weren't cast this way on the bottom of your library in a random order.
        this.addAbility(new SimpleStaticAbility(new ArchmageAscensionReplacementEffect()));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
    }

    private UnpredictableCyclone(final UnpredictableCyclone card) {
        super(card);
    }

    @Override
    public UnpredictableCyclone copy() {
        return new UnpredictableCyclone(this);
    }
}

class ArchmageAscensionReplacementEffect extends ReplacementEffectImpl {

    ArchmageAscensionReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a cycling ability of another nonland card would cause you to draw a card, " +
                "instead exile cards from the top of your library until you exile a card " +
                "that shares a card type with the cycled card. You may cast that card without paying its mana cost. " +
                "Then put the exiled cards that weren't cast this way on the bottom of your library in a random order.";
    }

    private ArchmageAscensionReplacementEffect(final ArchmageAscensionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ArchmageAscensionReplacementEffect copy() {
        return new ArchmageAscensionReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        Card sourceCard = game.getCard(event.getSourceId());
        if (player == null || sourceCard == null || sourceCard.isLand()) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card toCast = null;
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.getCardType().stream().anyMatch(sourceCard.getCardType()::contains)) {
                toCast = card;
                break;
            }
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        if (toCast != null && player.chooseUse(outcome, "Cast the exiled card?", source, game)) {
            game.getState().setValue("PlayFromNotOwnHandZone" + toCast.getId(), Boolean.TRUE);
            Boolean cardWasCast = player.cast(player.chooseAbilityForCast(toCast, game, true),
                    game, true, new MageObjectReference(source.getSourceObject(game), game));
            game.getState().setValue("PlayFromNotOwnHandZone" + toCast.getId(), null);
            if (cardWasCast) {
                cards.remove(toCast);
            }
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CYCLE_DRAW;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
