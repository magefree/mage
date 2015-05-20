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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public class DistortingWake extends CardImpl {

    public DistortingWake(UUID ownerId) {
        super(ownerId, 107, "Distorting Wake", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{U}{U}{U}");
        this.expansionSetCode = "C14";


        // Return X target nonland permanents to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target nonland permanents to their owners' hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    public DistortingWake(final DistortingWake card) {
        super(card);
    }

    @Override
    public DistortingWake copy() {
        return new DistortingWake(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int xValue = ability.getManaCostsToPay().getX();
            Target target = new TargetNonlandPermanent(xValue, xValue,
                    new FilterNonlandPermanent(xValue + " target nonland permanent(s)"), false);
            ability.getTargets().clear();
            ability.getTargets().add(target);
        }
    }

}
