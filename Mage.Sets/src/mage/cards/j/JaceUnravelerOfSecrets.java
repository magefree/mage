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
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.command.emblems.JaceUnravelerOfSecretsEmblem;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.SpellsCastWatcher;

/**
 * import mage.game.command.emblems.JaceUnravelerOfSecretsEmblem;
 *
 * @author LevelX2
 */
public class JaceUnravelerOfSecrets extends CardImpl {

    public JaceUnravelerOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
        this.subtype.add("Jace");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Scry 1, then draw a card.
        Ability ability = new LoyaltyAbility(new ScryEffect(1), 1);
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText(", then draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -2: Return target creature to its owner's hand.
        ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -8: You get an emblem with "Whenever an opponent casts his or her first spell each turn, counter that spell."
        LoyaltyAbility ability2 = new LoyaltyAbility(new GetEmblemEffect(new JaceUnravelerOfSecretsEmblem()), -8);
        this.addAbility(ability2, new SpellsCastWatcher());

    }

    public JaceUnravelerOfSecrets(final JaceUnravelerOfSecrets card) {
        super(card);
    }

    @Override
    public JaceUnravelerOfSecrets copy() {
        return new JaceUnravelerOfSecrets(this);
    }
}
