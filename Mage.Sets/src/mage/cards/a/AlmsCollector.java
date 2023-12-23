
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AlmsCollector extends CardImpl {

    public AlmsCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CAT, SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // If an opponent would draw two or more cards, instead you and that player each draw a card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AlmsCollectorReplacementEffect()));
    }

    private AlmsCollector(final AlmsCollector card) {
        super(card);
    }

    @Override
    public AlmsCollector copy() {
        return new AlmsCollector(this);
    }
}

class AlmsCollectorReplacementEffect extends ReplacementEffectImpl {

    public AlmsCollectorReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.DrawCard);
        staticText = "If an opponent would draw two or more cards, instead you and that player each draw a card";
    }

    private AlmsCollectorReplacementEffect(final AlmsCollectorReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AlmsCollectorReplacementEffect copy() {
        return new AlmsCollectorReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(event.getPlayerId());
        if (controller != null && opponent != null) {
            controller.drawCards(1, source, game, event);
            opponent.drawCards(1, source, game, event);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARDS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getAmount() > 1) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
                return true;
            }
        }
        return false;
    }
}
