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
package mage.cards.c;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class Crypsis extends CardImpl {

    static final FilterCard filter = new FilterCard("creatures your opponents control");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public Crypsis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Target creature you control gains protection from creatures your opponents control until end of turn. Untap it.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new ProtectionAbility(filter), Duration.EndOfTurn));
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap it.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        
    }

    public Crypsis(final Crypsis card) {
        super(card);
    }

    @Override
    public Crypsis copy() {
        return new Crypsis(this);
    }
}
