
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class UntapAllDuringEachOtherPlayersUntapStepEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;

    public UntapAllDuringEachOtherPlayersUntapStepEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Untap);
        this.filter = filter;
        staticText = setStaticText();
    }

    public UntapAllDuringEachOtherPlayersUntapStepEffect(final UntapAllDuringEachOtherPlayersUntapStepEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public UntapAllDuringEachOtherPlayersUntapStepEffect copy() {
        return new UntapAllDuringEachOtherPlayersUntapStepEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Boolean applied = (Boolean) game.getState().getValue(source.getSourceId() + "applied");
        if (applied == null) {
            applied = Boolean.FALSE;
        }
        if (!applied && layer == Layer.RulesEffects) {
            if (!source.isControlledBy(game.getActivePlayerId()) && game.getStep().getType() == PhaseStep.UNTAP) {
                game.getState().setValue(source.getSourceId() + "applied", true);
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    boolean untap = true;
                    for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(permanent, game).keySet()) {
                        untap &= effect.canBeUntapped(permanent, source, game);
                    }
                    if (untap) {
                        permanent.untap(game);
                    }
                }
            }
        } else if (applied && layer == Layer.RulesEffects) {
            if (game.getStep().getType() == PhaseStep.END_TURN) {
                game.getState().setValue(source.getSourceId() + "applied", false);
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }

    private String setStaticText() {
        StringBuilder sb = new StringBuilder("Untap ");
        if (!filter.getMessage().startsWith("each")) {
            sb.append("all ");
        }
        sb.append(filter.getMessage());
        sb.append(" during each other player's untap step");
        return sb.toString();
    }
}
