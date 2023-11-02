
package mage.cards.w;

import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class WasteNot extends CardImpl {

    public WasteNot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Whenever an opponent discards a creature card, create a 2/2 black Zombie creature token.
        this.addAbility(new WasteNotCreatureTriggeredAbility());

        // Whenever an opponent discards a land card, add {B}{B}.
        this.addAbility(new WasteNotLandTriggeredAbility());

        // Whenever an opponent discards a noncreature, nonland card, draw a card.
        this.addAbility(new WasteNotOtherTriggeredAbility());
    }

    private WasteNot(final WasteNot card) {
        super(card);
    }

    @Override
    public WasteNot copy() {
        return new WasteNot(this);
    }
}

class WasteNotCreatureTriggeredAbility extends TriggeredAbilityImpl {

    WasteNotCreatureTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken()), false);
    }

    private WasteNotCreatureTriggeredAbility(final WasteNotCreatureTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WasteNotCreatureTriggeredAbility copy() {
        return new WasteNotCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
            Card discarded = game.getCard(event.getTargetId());
            if (discarded != null && discarded.isCreature(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent discards a creature card, create a 2/2 black Zombie creature token.";
    }
}

class WasteNotLandTriggeredAbility extends TriggeredAbilityImpl {

    WasteNotLandTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana(2)), false);
    }

    private WasteNotLandTriggeredAbility(final WasteNotLandTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WasteNotLandTriggeredAbility copy() {
        return new WasteNotLandTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
            Card discarded = game.getCard(event.getTargetId());
            return discarded != null && discarded.isLand(game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent discards a land card, add {B}{B}.";
    }
}

class WasteNotOtherTriggeredAbility extends TriggeredAbilityImpl {

    WasteNotOtherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private WasteNotOtherTriggeredAbility(final WasteNotOtherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WasteNotOtherTriggeredAbility copy() {
        return new WasteNotOtherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
            Card discarded = game.getCard(event.getTargetId());
            if (discarded != null && !discarded.isLand(game) && !discarded.isCreature(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent discards a noncreature, nonland card, draw a card.";
    }
}
