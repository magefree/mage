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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author LevelX2
 */
public class JaceUnravelerOfSecrets extends CardImpl {

    public JaceUnravelerOfSecrets(UUID ownerId) {
        super(ownerId, 69, "Jace, Unraveler of Secrets", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
        this.expansionSetCode = "SOI";
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

/**
 * Emblem: "Whenever an opponent casts his or her first spell each turn, counter
 * that spell."
 */
class JaceUnravelerOfSecretsEmblem extends Emblem {

    public JaceUnravelerOfSecretsEmblem() {
        this.setName("EMBLEM: Jace, Unraveler of Secrets");
        Effect effect = new CounterTargetEffect();
        effect.setText("counter that spell");
        this.getAbilities().add(new JaceUnravelerOfSecretsTriggertAbility(effect, false));
    }
}

class JaceUnravelerOfSecretsTriggertAbility extends SpellCastOpponentTriggeredAbility {

    public JaceUnravelerOfSecretsTriggertAbility(Effect effect, boolean optional) {
        super(effect, optional);
    }

    public JaceUnravelerOfSecretsTriggertAbility(SpellCastOpponentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpellCastOpponentTriggeredAbility copy() {
        return super.copy(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getName());
            if (watcher != null) {
                if (watcher.getSpellsCastThisTurn(event.getPlayerId()) == null) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts his or her first spell each turn, counter that spell.";
    }

}
