package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public class ExileUntilSourceLeavesEffect extends OneShotEffect {

    private final Zone returnToZone;

    /**
     * Exiles target(s) until source leaves battlefield
     * Includes effect that returns exiled card to battlefield when source leaves
     */
    public ExileUntilSourceLeavesEffect() {
        this(Zone.BATTLEFIELD);
    }

    /**
     * Exiles target(s) until source leaves battlefield
     * @param returnToZone The zone to which the exiled card will be returned when source leaves
     */
    public ExileUntilSourceLeavesEffect(Zone returnToZone) {
        super(Outcome.Removal);
        this.returnToZone = returnToZone;
    }

    public ExileUntilSourceLeavesEffect(final ExileUntilSourceLeavesEffect effect) {
        super(effect);
        this.returnToZone = effect.returnToZone;
    }

    @Override
    public ExileUntilSourceLeavesEffect copy() {
        return new ExileUntilSourceLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        // If source permanent leaves the battlefield before its triggered ability resolves, the target permanent won't be exiled.
        if (permanent == null) {
            return false;
        }

        ExileTargetEffect effect = new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
        if (targetPointer != null) {  // Grasping Giant
            effect.setTargetPointer(targetPointer);
        }
        if (effect.apply(game, source)) {
            game.addDelayedTriggeredAbility(new OnLeaveReturnExiledAbility(returnToZone), source);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "exile " + getTargetPointer().describeTargets(mode.getTargets(), "that creature") + " until {this} leaves the battlefield";
    }
}
