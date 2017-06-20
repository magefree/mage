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
package mage.cards.d;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.command.emblems.DackFaydenEmblem;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class DackFayden extends CardImpl {

    public DackFayden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{R}");
        this.subtype.add("Dack");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Target player draws two cards, then discards two cards.
        LoyaltyAbility ability = new LoyaltyAbility(new DrawCardTargetEffect(2), 1);
        Effect effect = new DiscardTargetEffect(2);
        effect.setText(", then discards two cards");
        ability.addEffect(effect);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -2: Gain control of target artifact.
        effect = new GainControlTargetEffect(Duration.EndOfGame, true);
        effect.setText("Gain control of target artifact");
        ability = new LoyaltyAbility(effect, -2);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // -6: You get an emblem with "Whenever you cast a spell that targets one or more permanents, gain control of those permanents."
        effect = new GetEmblemEffect(new DackFaydenEmblem());
        effect.setText("You get an emblem with \"Whenever you cast a spell that targets one or more permanents, gain control of those permanents.\"");
        ability = new LoyaltyAbility(effect, -6);
        this.addAbility(ability);
    }

    public DackFayden(final DackFayden card) {
        super(card);
    }

    @Override
    public DackFayden copy() {
        return new DackFayden(this);
    }
}
