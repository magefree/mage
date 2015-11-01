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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class LithomancersFocus extends CardImpl {

    public LithomancersFocus(UUID ownerId) {
        super(ownerId, 38, "Lithomancer's Focus", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "BFZ";

        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Prevent all damage that would be dealt to that creature this turn by colorless sources.
        this.getSpellAbility().addEffect(new LithomancersFocusPreventDamageToTargetEffect());
    }

    public LithomancersFocus(final LithomancersFocus card) {
        super(card);
    }

    @Override
    public LithomancersFocus copy() {
        return new LithomancersFocus(this);
    }
}

class LithomancersFocusPreventDamageToTargetEffect extends PreventionEffectImpl {

    public LithomancersFocusPreventDamageToTargetEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to that creature this turn by colorless sources";
    }

    public LithomancersFocusPreventDamageToTargetEffect(final LithomancersFocusPreventDamageToTargetEffect effect) {
        super(effect);
    }

    @Override
    public LithomancersFocusPreventDamageToTargetEffect copy() {
        return new LithomancersFocusPreventDamageToTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event.getTargetId().equals(targetPointer.getFirst(game, source))) {
            MageObject object = game.getObject(event.getSourceId());
            return object != null && object.getColor(game).isColorless();
        }
        return false;
    }

}
