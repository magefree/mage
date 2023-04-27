package mage.abilities.effects.common.ruleModifying;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class CantCastOrActivateOpponentsYourTurnEffect extends ContinuousRuleModifyingEffectImpl {

    public CantCastOrActivateOpponentsYourTurnEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "during your turn, your opponents can't cast spells " +
                "or activate abilities of artifacts, creatures, or enchantments";
    }

    public CantCastOrActivateOpponentsYourTurnEffect(final CantCastOrActivateOpponentsYourTurnEffect effect) {
        super(effect);
    }

    @Override
    public CantCastOrActivateOpponentsYourTurnEffect copy() {
        return new CantCastOrActivateOpponentsYourTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        MageObject mageObject = game.getObject(source);
        if (activePlayer == null || mageObject == null) {
            return null;
        }
        return "You can't cast spells or activate abilities of artifacts, " +
                "creatures, or enchantments during the turns of " +
                activePlayer.getLogName() + " (" + mageObject.getLogName() + ')';
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL
                || event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.isActivePlayer(source.getControllerId())
                || !game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        switch (event.getType()) {
            case CAST_SPELL:
                return true;
            case ACTIVATE_ABILITY:
                Permanent permanent = game.getPermanent(event.getSourceId());
                return permanent != null
                        && (permanent.isArtifact(game)
                        || permanent.isCreature(game)
                        || permanent.isEnchantment(game));
        }
        return false;
    }
}