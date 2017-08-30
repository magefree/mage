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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.game.command.emblems.ObNixilisReignitedEmblem;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class ObNixilisReignited extends CardImpl {

    public ObNixilisReignited(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Nixilis");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: You draw a card and you lose 1 life.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("You draw a card");
        LoyaltyAbility ability1 = new LoyaltyAbility(effect, 1);
        effect = new LoseLifeSourceControllerEffect(1);
        effect.setText("and you lose 1 life");
        ability1.addEffect(effect);
        this.addAbility(ability1);

        // -3: Destroy target creature.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);

        // -8: Target opponent gets an emblem with "Whenever a player draws a card, you lose 2 life."
        effect = new GetEmblemTargetPlayerEffect(new ObNixilisReignitedEmblem());
        effect.setText("Target opponent gets an emblem with \"Whenever a player draws a card, you lose 2 life.\"");
        LoyaltyAbility ability3 = new LoyaltyAbility(effect, -8);
        ability3.addTarget(new TargetOpponent());
        this.addAbility(ability3);
    }

    public ObNixilisReignited(final ObNixilisReignited card) {
        super(card);
    }

    @Override
    public ObNixilisReignited copy() {
        return new ObNixilisReignited(this);
    }
}
