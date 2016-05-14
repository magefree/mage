/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.dissension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
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
public class TrialError extends SplitCard {

    private static final FilterSpell filter = new FilterSpell("multicolored spell");

    static {
        filter.add(new MulticoloredPredicate());
    }

    public TrialError(UUID ownerId) {
        super(ownerId, 158, "Trial", "Error", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}{U}", "{U}{B}", false);
        this.expansionSetCode = "DIS";

        // Trial
        // Return all creatures blocking or blocked by target creature to their owner's hand.
        getLeftHalfCard().getSpellAbility().addEffect(new TrialEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Error
        // Counter target multicolored spell.
        getRightHalfCard().getSpellAbility().addEffect(new CounterTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetSpell(filter));

    }

    public TrialError(final TrialError card) {
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

    public TrialEffect(final TrialEffect effect) {
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
