
package mage.cards.s;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class SpiritualFocus extends CardImpl {

    public SpiritualFocus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever a spell or ability an opponent controls causes you to discard a card, you gain 2 life and you may draw a card.
        this.addAbility(new SpiritualFocusTriggeredAbility());
    }

    private SpiritualFocus(final SpiritualFocus card) {
        super(card);
    }

    @Override
    public SpiritualFocus copy() {
        return new SpiritualFocus(this);
    }
}

class SpiritualFocusTriggeredAbility extends TriggeredAbilityImpl {

    public SpiritualFocusTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(2), false);
        this.addEffect(new SpiritualFocusDrawCardEffect());
    }

    private SpiritualFocusTriggeredAbility(final SpiritualFocusTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpiritualFocusTriggeredAbility copy() {
        return new SpiritualFocusTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null) {
            if (game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId())) {
                Permanent permanent = game.getPermanent(getSourceId());
                if (permanent != null) {
                    if (Objects.equals(permanent.getControllerId(), event.getPlayerId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability an opponent controls causes you to discard a card, you gain 2 life and you may draw a card.";
    }
}

class SpiritualFocusDrawCardEffect extends OneShotEffect {

    public SpiritualFocusDrawCardEffect() {
        super(Outcome.DrawCard);
    }

    private SpiritualFocusDrawCardEffect(final SpiritualFocusDrawCardEffect effect) {
        super(effect);
    }

    @Override
    public SpiritualFocusDrawCardEffect copy() {
        return new SpiritualFocusDrawCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && permanent != null) {
            if (player.chooseUse(outcome, "Draw a card (" + permanent.getLogName() + ')', source, game)) {
                player.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}
