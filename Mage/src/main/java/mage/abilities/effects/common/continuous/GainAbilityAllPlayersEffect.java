package mage.abilities.effects.common.continuous;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author padfoot
 */

public class GainAbilityAllPlayersEffect extends ContinuousEffectImpl {

    private TargetController targetController;
    protected Ability ability;

    public GainAbilityAllPlayersEffect(Ability ability) {
        this(ability, Duration.WhileOnBattlefield);
    }

    public GainAbilityAllPlayersEffect(Ability ability, Duration duration) {
        this(ability, duration, TargetController.ANY);
    }
    
    /**
     * @param ability
     * @param duration custom - effect will be discarded as soon there is no sourceId - permanent on the battlefield
     */
    public GainAbilityAllPlayersEffect(Ability ability, Duration duration, TargetController targetController) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability.copy();
	this.targetController = targetController;
        this.staticText = "Players gain " + ability.getRule();
        if (!duration.toString().isEmpty()) {
            staticText += ' ' + duration.toString();
        }
    }

    protected GainAbilityAllPlayersEffect(final GainAbilityAllPlayersEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
	this.targetController = effect.targetController;
    }

    @Override
    public GainAbilityAllPlayersEffect copy() {
        return new GainAbilityAllPlayersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
	switch (targetController) {
	    case ANY:
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.addAbility(ability);
			if (duration == Duration.Custom) {
                            if (game.getPermanent(source.getSourceId()) == null) {
                                discard();
                            }
                        }
                    } else {
			discard();
		    }
                }
                break;
            case OPPONENT:
                for (UUID playerId : game.getOpponents(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.addAbility(ability);
			if (duration == Duration.Custom) {
                            if (game.getPermanent(source.getSourceId()) == null) {
                                discard();
                            }
                        }
                    } else {
			discard();
                    }
		}
                break;
        }
        return true;
    }
}
