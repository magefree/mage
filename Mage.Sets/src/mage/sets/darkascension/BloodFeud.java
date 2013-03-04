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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author intimidatingant
 */
public class BloodFeud extends CardImpl<BloodFeud> {

    public BloodFeud(UUID ownerId) {
        super(ownerId, 83, "Blood Feud", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");
        this.expansionSetCode = "DKA";

        this.color.setRed(true);

        // Target creature fights another target creature.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
        this.getSpellAbility().addTarget(new TargetOtherCreaturePermanent(true));
    }

    public BloodFeud(final BloodFeud card) {
        super(card);
    }

    @Override
    public BloodFeud copy() {
        return new BloodFeud(this);
    }
}

class TargetOtherCreaturePermanent extends TargetCreaturePermanent {

    public TargetOtherCreaturePermanent(boolean required) {
        super(true);
    }

    public TargetOtherCreaturePermanent(final TargetOtherCreaturePermanent target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (source.getTargets().get(0).getTargets().contains(id)) {
            return false;
        }
        return super.canTarget(controllerId, id, source, game);
    }

    @Override
    public TargetOtherCreaturePermanent copy() {
        return new TargetOtherCreaturePermanent(this);
    }

}
