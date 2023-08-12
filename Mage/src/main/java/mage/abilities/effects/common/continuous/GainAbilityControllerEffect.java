

package mage.abilities.effects.common.continuous;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityControllerEffect extends ContinuousEffectImpl {

    protected Ability ability;

    /**
     * Add ability with Duration.WhileOnBattlefield
     *
     * @param ability
     */
    public GainAbilityControllerEffect(Ability ability) {
        this(ability, Duration.WhileOnBattlefield);
    }

    /**
     * @param ability
     * @param duration custom - effect will be discarded as soon there is no sourceId - permanent on the battlefield
     */
    public GainAbilityControllerEffect(Ability ability, Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        staticText = "You have " + ability.getRule();
        if (!duration.toString().isEmpty()) {
            staticText += ' ' + duration.toString();
        }
    }

    protected GainAbilityControllerEffect(final GainAbilityControllerEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public GainAbilityControllerEffect copy() {
        return new GainAbilityControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.addAbility(ability);
            if (duration == Duration.Custom) {
                if (game.getPermanent(source.getSourceId()) == null) {
                    discard();
                }
            }
            return true;
        } else {
            discard();
        }
        return false;
    }

}
