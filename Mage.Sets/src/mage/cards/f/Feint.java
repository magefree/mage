package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class Feint extends CardImpl {

    public Feint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Tap all creatures blocking target attacking creature. Prevent all combat damage that would be dealt this turn by that creature and each creature blocking it.
        this.getSpellAbility().addEffect(new FeintEffect());
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, true)
                .setText("Prevent all combat damage that would be dealt this turn by that creature and each creature blocking it"));
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private Feint(final Feint card) {
        super(card);
    }

    @Override
    public Feint copy() {
        return new Feint(this);
    }

}

class FeintEffect extends OneShotEffect {

    public FeintEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "tap all creatures blocking target attacking creature";
    }

    public FeintEffect(final FeintEffect effect) {
        super(effect);
    }

    @Override
    public FeintEffect copy() {
        return new FeintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && creature != null) {
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getAttackers().contains(creature.getId())) {
                    for (UUID blockerId : combatGroup.getBlockers()) {
                        Permanent blocker = game.getPermanent(blockerId);
                        if (blocker != null) {
                            blocker.tap(source, game);
                            PreventionEffect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
                            effect.setTargetPointer(new FixedTarget(blocker.getId(), game));
                            game.addEffect(effect, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
