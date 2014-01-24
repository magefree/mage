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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public class FallOfTheHammer extends CardImpl<FallOfTheHammer> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    public FallOfTheHammer(UUID ownerId) {
        super(ownerId, 93, "Fall of the Hammer", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "BNG";

        this.color.setRed(true);

        // Target creature you control deals damage equal to its power to another target creature.
        Effect effect = new DamageTargetEffect(new TargetPermanentPowerCount());
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("Target creature you control deals damage equal to its power to another target creature");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(true));
        this.getSpellAbility().addTarget(new FallOfTheHammerTargetCreaturePermanent(filter, true));
    }

    public FallOfTheHammer(final FallOfTheHammer card) {
        super(card);
    }

    @Override
    public FallOfTheHammer copy() {
        return new FallOfTheHammer(this);
    }
}

class FallOfTheHammerTargetCreaturePermanent extends TargetCreaturePermanent {

    public FallOfTheHammerTargetCreaturePermanent(FilterCreaturePermanent filter, boolean required) {
        super(filter, required);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (source.getTargets().getFirstTarget().equals(id)) {
            return false;
        }
        return super.canTarget(id, source, game);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (source.getTargets().getFirstTarget().equals(id)) {
            return false;
        }
        return super.canTarget(controllerId, id, source, game);
    }

}
