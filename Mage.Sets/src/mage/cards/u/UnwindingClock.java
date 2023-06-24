package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class UnwindingClock extends CardImpl {

    public UnwindingClock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Untap all artifacts you control during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UnwindingClockEffect()));
    }

    private UnwindingClock(final UnwindingClock card) {
        super(card);
    }

    @Override
    public UnwindingClock copy() {
        return new UnwindingClock(this);
    }
}

class UnwindingClockEffect extends ContinuousEffectImpl {

    private static FilterArtifactPermanent filter = new FilterArtifactPermanent();

    public UnwindingClockEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Untap all artifacts you control during each other player's untap step";
    }

    public UnwindingClockEffect(final UnwindingClockEffect effect) {
        super(effect);
    }

    @Override
    public UnwindingClockEffect copy() {
        return new UnwindingClockEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean applied = Boolean.TRUE.equals(game.getState().getValue(source.getSourceId() + "applied"));
        if (!applied && layer == Layer.RulesEffects) {
            if (!game.isActivePlayer(source.getControllerId()) && game.getTurnStepType() == PhaseStep.UNTAP) {
                game.getState().setValue(source.getSourceId() + "applied", true);
                for (Permanent artifact : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    boolean untap = true;
                    for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(artifact, game).keySet()) {
                        untap &= effect.canBeUntapped(artifact, source, game, true);
                    }
                    if (untap) {
                        artifact.untap(game);
                    }
                }
            }
        } else if (applied && layer == Layer.RulesEffects) {
            if (game.getTurnStepType() == PhaseStep.END_TURN) {
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
}
