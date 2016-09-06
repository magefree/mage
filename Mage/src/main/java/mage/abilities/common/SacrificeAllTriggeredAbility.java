/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SacrificeAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final TargetController sacrificingPlayer;

    public SacrificeAllTriggeredAbility(Effect effect, FilterPermanent filter, TargetController sacrificingPlayer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.sacrificingPlayer = sacrificingPlayer;
    }

    public SacrificeAllTriggeredAbility(final SacrificeAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.sacrificingPlayer = ability.sacrificingPlayer;
    }

    @Override
    public SacrificeAllTriggeredAbility copy() {
        return new SacrificeAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean sacrificed = false;
        switch (sacrificingPlayer) {
            case YOU:
                if (event.getPlayerId().equals(getControllerId())) {
                    sacrificed = true;
                }
                break;
            case OPPONENT:
                Player controller = game.getPlayer(getControllerId());
                if (controller == null || !controller.hasOpponent(event.getPlayerId(), game)) {
                    sacrificed = true;
                }
                break;
        }
        Permanent sacrificedPermanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return sacrificed && sacrificedPermanent != null && filter.match(sacrificedPermanent, getSourceId(), getControllerId(), game);
    }

    @Override
    public String getRule() {
        String targetControllerText = "a player sacrifices ";
        switch (sacrificingPlayer) {
            case YOU:
                targetControllerText = "you sacrifice ";
                break;
            case OPPONENT:
                targetControllerText = "an opponent sacrifices ";
                break;
        }
        return "Whenever " + targetControllerText + filter.getMessage() + ", " + super.getRule();
    }
}
