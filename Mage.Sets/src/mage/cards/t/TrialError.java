
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TrialError extends SplitCard {

    private static final FilterSpell filter = new FilterSpell("multicolored spell");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public TrialError(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}", "{U}{B}", SpellAbilityType.SPLIT);

        // Trial
        // Return all creatures blocking or blocked by target creature to their owner's hand.
        getLeftHalfCard().getSpellAbility().addEffect(new TrialEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Error
        // Counter target multicolored spell.
        getRightHalfCard().getSpellAbility().addEffect(new CounterTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetSpell(filter));

    }

    private TrialError(final TrialError card) {
        super(card);
    }

    @Override
    public TrialError copy() {
        return new TrialError(this);
    }
}

class TrialEffect extends OneShotEffect {

    public TrialEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return all creatures blocking or blocked by target creature to their owner's hand";
    }

    private TrialEffect(final TrialEffect effect) {
        super(effect);
    }

    @Override
    public TrialEffect copy() {
        return new TrialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && creature != null) {
            Set<Card> toHand = new HashSet<>();
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getBlockers().contains(creature.getId())) {
                    for (UUID attackerId : combatGroup.getAttackers()) {
                        Permanent attacker = game.getPermanent(attackerId);
                        if (attacker != null) {
                            toHand.add(attacker);
                        }
                    }
                } else if (combatGroup.getAttackers().contains(creature.getId())) {
                    for (UUID blockerId : combatGroup.getBlockers()) {
                        Permanent blocker = game.getPermanent(blockerId);
                        if (blocker != null) {
                            toHand.add(blocker);
                        }
                    }
                }
            }
            controller.moveCards(toHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
