
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author LevelX2
 */
public final class MirrorMatch extends CardImpl {

    public MirrorMatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}{U}");

        // Cast Mirror Match only during the declare blockers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(PhaseStep.DECLARE_BLOCKERS));

        // For each creature attacking you or a planeswalker you control, create a token that's a copy of that creature blocking that creature. Exile those tokens at end of combat.
        this.getSpellAbility().addEffect(new MirrorMatchEffect());

    }

    private MirrorMatch(final MirrorMatch card) {
        super(card);
    }

    @Override
    public MirrorMatch copy() {
        return new MirrorMatch(this);
    }
}

class MirrorMatchEffect extends OneShotEffect {

    public MirrorMatchEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each creature attacking you or a planeswalker you control, create a token that's a copy of that creature blocking that creature. Exile those tokens at end of combat";
    }

    public MirrorMatchEffect(final MirrorMatchEffect effect) {
        super(effect);
    }

    @Override
    public MirrorMatchEffect copy() {
        return new MirrorMatchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID attackerId : game.getCombat().getAttackers()) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null
                        && source.isControlledBy(game.getCombat().getDefendingPlayerId(attackerId, game))) {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, false);
                    effect.setTargetPointer(new FixedTarget(attacker, game));
                    effect.apply(game, source);
                    CombatGroup group = game.getCombat().findGroup(attacker.getId());
                    boolean isCreature = false;
                    if (group != null) {
                        for (Permanent addedToken : effect.getAddedPermanents()) {
                            if (addedToken.isCreature(game)) {
                                group.addBlockerToGroup(addedToken.getId(), attackerId, game);
                                isCreature = true;
                            }
                        }
                        ExileTargetEffect exileEffect = new ExileTargetEffect("Exile those tokens at end of combat");
                        exileEffect.setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game));
                        game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), source);
                        if (isCreature) {
                            group.pickBlockerOrder(attacker.getControllerId(), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
