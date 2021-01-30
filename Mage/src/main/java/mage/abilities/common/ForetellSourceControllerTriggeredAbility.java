/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class ForetellSourceControllerTriggeredAbility extends TriggeredAbilityImpl {

    public ForetellSourceControllerTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public ForetellSourceControllerTriggeredAbility(final ForetellSourceControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAKEN_SPECIAL_ACTION;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        //UUID specialAction = event.getTargetId();
        Card card = game.getCard(event.getSourceId());
        Player player = game.getPlayer(event.getPlayerId());
        for (Ability a : card.getAbilities()) {
            if (player.getId() == controllerId
                    && (a instanceof SpecialAction)
                    && a.getRule().endsWith("and exile this card from your hand face down. Cast it on a later turn for its foretell cost.)</i>")) {
                return true;
            }
        }
        // if the ability is added to cards via effect
        for (Ability a : game.getState().getAllOtherAbilities(card.getId())) {
            if (player.getId() == controllerId
                    && (a instanceof SpecialAction)
                    && a.getRule().endsWith("and exile this card from your hand face down. Cast it on a later turn for its foretell cost.)</i>")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you foretell a card, " + super.getRule();
    }

    @Override
    public ForetellSourceControllerTriggeredAbility copy() {
        return new ForetellSourceControllerTriggeredAbility(this);
    }

}
