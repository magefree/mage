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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class RivalsDuel extends CardImpl<RivalsDuel> {

    public RivalsDuel(UUID ownerId) {
        super(ownerId, 51, "Rivals' Duel", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "PC2";

        this.color.setRed(true);

        // Choose two target creatures that share no creature types. Those creatures fight each other.
        this.getSpellAbility().addTarget(new TargetCreaturePermanentWithDifferentTypes(2));
        this.getSpellAbility().addEffect(new RivalsDuelFightTargetsEffect());
    }

    public RivalsDuel(final RivalsDuel card) {
        super(card);
    }

    @Override
    public RivalsDuel copy() {
        return new RivalsDuel(this);
    }
}

class TargetCreaturePermanentWithDifferentTypes extends TargetCreaturePermanent {

    public TargetCreaturePermanentWithDifferentTypes(int numTargets) {
        super(numTargets);
    }

    public TargetCreaturePermanentWithDifferentTypes(final TargetCreaturePermanentWithDifferentTypes target) {
        super(target);
    }

    @Override
    public TargetCreaturePermanentWithDifferentTypes copy() {
        return new TargetCreaturePermanentWithDifferentTypes(this);
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        if (super.canTarget(id, game)) {
            Permanent creature = game.getPermanent(id);
            if (creature != null) {
                for (Object object : getTargets()) {
                    UUID targetId = (UUID) object;
                    Permanent selectedCreature = game.getPermanent(targetId);
                    if (CardUtil.shareSubtypes(creature, selectedCreature)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class RivalsDuelFightTargetsEffect extends OneShotEffect<RivalsDuelFightTargetsEffect> {

    public RivalsDuelFightTargetsEffect() {
        super(Outcome.Damage);
        staticText = "Choose two target creatures that share no creature types. Those creatures fight each other";
    }

    public RivalsDuelFightTargetsEffect(final RivalsDuelFightTargetsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetCreaturePermanentWithDifferentTypes target = (TargetCreaturePermanentWithDifferentTypes) source.getTargets().get(0);
        Permanent creature1 = game.getPermanent(target.getFirstTarget());
        Permanent creature2 = game.getPermanent((UUID) target.getTargets().get(1));
        // 20110930 - 701.10
        if (creature1 != null && creature2 != null) {
            if (creature1.getCardType().contains(CardType.CREATURE) && creature2.getCardType().contains(CardType.CREATURE)) {
                creature1.damage(creature2.getPower().getValue(), creature2.getId(), game, true, false);
                creature2.damage(creature1.getPower().getValue(), creature1.getId(), game, true, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public RivalsDuelFightTargetsEffect copy() {
        return new RivalsDuelFightTargetsEffect(this);
    }
}
