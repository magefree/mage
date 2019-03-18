
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class FogPatch extends CardImpl {

    public FogPatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Cast Fog Patch only during the declare blockers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, PhaseStep.DECLARE_BLOCKERS, null, "Cast this spell only during the declare blockers step"));

        // Attacking creatures become blocked.
        this.getSpellAbility().addEffect(new FogPatchEffect());
    }

    public FogPatch(final FogPatch card) {
        super(card);
    }

    @Override
    public FogPatch copy() {
        return new FogPatch(this);
    }
}

class FogPatchEffect extends OneShotEffect {

    public FogPatchEffect() {
        super(Outcome.Benefit);
        this.staticText = "Attacking creatures become blocked";
    }

    public FogPatchEffect(final FogPatchEffect effect) {
        super(effect);
    }

    @Override
    public FogPatchEffect copy() {
        return new FogPatchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID attackers : game.getCombat().getAttackers()) {
                Permanent attacker = game.getPermanent(attackers);
                if (attacker != null) {
                    CombatGroup combatGroup = game.getCombat().findGroup(attacker.getId());
                    if (combatGroup != null) {
                        combatGroup.setBlocked(true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
