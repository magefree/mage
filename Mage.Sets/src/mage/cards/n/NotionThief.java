
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

/**
 *
 * @author LevelX2
 */
public final class NotionThief extends CardImpl {

    public NotionThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // If an opponent would draw a card except the first one they draw in each of their draw steps, instead that player skips that draw and you draw a card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NotionThiefReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());

    }

    private NotionThief(final NotionThief card) {
        super(card);
    }

    @Override
    public NotionThief copy() {
        return new NotionThief(this);
    }
}

class NotionThiefReplacementEffect extends ReplacementEffectImpl {

    public NotionThiefReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an opponent would draw a card except the first one they draw in each of their draw steps, instead that player skips that draw and you draw a card";
    }

    public NotionThiefReplacementEffect(final NotionThiefReplacementEffect effect) {
        super(effect);
    }

    @Override
    public NotionThiefReplacementEffect copy() {
        return new NotionThiefReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game, event);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            if (game.isActivePlayer(event.getPlayerId()) && game.getTurnStepType() == PhaseStep.DRAW) {
                CardsDrawnDuringDrawStepWatcher watcher = game.getState().getWatcher(CardsDrawnDuringDrawStepWatcher.class);
                if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0) {
                    return true;
                }
            } else {
                // not an opponents players draw step, always replace the draw
                return true;
            }
        }
        return false;
    }
}
