
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author ayratn
 */
public class SacrificeTargetEffect extends OneShotEffect {

    protected UUID playerIdThatHasToSacrifice;

    public SacrificeTargetEffect() {
        this("");
    }

    public SacrificeTargetEffect(String text) {
        this(text, null);
    }

    /**
     *
     * @param text use this text as rule text for the effect
     * @param playerIdThatHasToSacrifice only this playerId has to sacrifice
     * (others can't)
     */
    public SacrificeTargetEffect(String text, UUID playerIdThatHasToSacrifice) {
        super(Outcome.Sacrifice);
        this.playerIdThatHasToSacrifice = playerIdThatHasToSacrifice;
        staticText = text;
    }

    public SacrificeTargetEffect(final SacrificeTargetEffect effect) {
        super(effect);
        this.playerIdThatHasToSacrifice = effect.playerIdThatHasToSacrifice;
    }

    @Override
    public SacrificeTargetEffect copy() {
        return new SacrificeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && (playerIdThatHasToSacrifice == null || playerIdThatHasToSacrifice.equals(permanent.getControllerId()))) {
                permanent.sacrifice(source, game);
                affectedTargets++;
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that permanent") + "'s controller sacrifices it";
    }
}
