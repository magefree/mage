package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrabraskHereticPraetor extends CardImpl {

    public UrabraskHereticPraetor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of your upkeep, exile the top card of your library. You may play it this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1, false),
                TargetController.YOU, false
        ));

        // At the beginning of each opponent's upkeep, the next time they would draw a card this turn, instead they exile the top card of their library. They may play it this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new UrabraskHereticPraetorEffect(), TargetController.OPPONENT, false
        ));
    }

    private UrabraskHereticPraetor(final UrabraskHereticPraetor card) {
        super(card);
    }

    @Override
    public UrabraskHereticPraetor copy() {
        return new UrabraskHereticPraetor(this);
    }
}

class UrabraskHereticPraetorEffect extends ReplacementEffectImpl {

    UrabraskHereticPraetorEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "the next time they would draw a card this turn, " +
                "instead they exile the top card of their library. They may play it this turn";
    }

    private UrabraskHereticPraetorEffect(final UrabraskHereticPraetorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.isActivePlayer(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            discard();
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
            CardUtil.makeCardPlayable(
                    game, source, card, Duration.EndOfTurn,
                    false, player.getId(), null
            );
        }
        discard();
        return true;
    }

    @Override
    public UrabraskHereticPraetorEffect copy() {
        return new UrabraskHereticPraetorEffect(this);
    }
}
