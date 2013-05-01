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

package mage.sets.invasion;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.game.permanent.token.ElephantToken;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */


public class AssaultBattery extends SplitCard<AssaultBattery> {

    public AssaultBattery(UUID ownerId) {
        super(ownerId, 295, "Assault - Battery", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{R}{3}{G}");
        this.expansionSetCode = "INV";

        this.color.setRed(true);
        this.color.setGreen(true);

        // Assault
        Card leftHalvCard = this.createLeftHalveCard("Assault", "{R}");
        // Assault deals 2 damage to target creature or player.
        Effect effect = new DamageTargetEffect(2);
        effect.setText("Assault deals 2 damage to target creature or player");
        leftHalvCard.getSpellAbility().addEffect(effect);
        leftHalvCard.getSpellAbility().addTarget(new TargetCreatureOrPlayer(true));

        // Battery
        Card rightHalvCard = this.createRightHalveCard("Battery", "{3}{G}");
        // Put a 3/3 green Elephant creature token onto the battlefield.
        rightHalvCard.getSpellAbility().addEffect(new CreateTokenEffect(new ElephantToken()));

    }

    public AssaultBattery(final AssaultBattery card) {
        super(card);
    }

    @Override
    public AssaultBattery copy() {
        return new AssaultBattery(this);
    }
}
