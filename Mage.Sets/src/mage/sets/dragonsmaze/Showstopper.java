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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public class Showstopper extends CardImpl<Showstopper> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter2.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public Showstopper (UUID ownerId) {
        super(ownerId, 102, "Showstopper", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{B}{R}");
        this.expansionSetCode = "DGM";
        this.color.setBlack(true);
        this.color.setRed(true);

        // Until end of turn, creatures you control gain "When this creature dies, it deals 2 damage to target creature an opponent controls."
        TriggeredAbility ability = new DiesTriggeredAbility(new DamageTargetEffect(2), false);
        Target target = new TargetCreaturePermanent(filter2);
        target.setRequired(true);
        ability.addTarget(target);
        Effect effect = new GainAbilityControlledEffect(ability, Duration.EndOfTurn, filter);
        effect.setText("Until end of turn, creatures you control gain \"When this creature dies, it deals 2 damage to target creature an opponent controls.\" ");
        this.getSpellAbility().addEffect(effect);

    }

    public Showstopper (final Showstopper card) {
        super(card);
    }

    @Override
    public Showstopper copy() {
        return new Showstopper(this);
    }

}
