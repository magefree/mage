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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DiscardEachPlayerEffect;
import mage.abilities.effects.common.LoseLifePlayersEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author LevelX2
 */
public class DeathCloud extends CardImpl<DeathCloud> {

    public DeathCloud(UUID ownerId) {
        super(ownerId, 76, "Death Cloud", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{B}{B}{B}");
        this.expansionSetCode = "MMA";

        this.color.setBlack(true);

        // Each player loses X life, discards X cards, sacrifices X creatures, then sacrifices X lands.
        DynamicValue xValue = new ManacostVariableValue();
        this.getSpellAbility().addEffect(new LoseLifePlayersEffect(xValue));
        Effect effect = new DiscardEachPlayerEffect(xValue, false);
        effect.setText(", discards X cards");
        this.getSpellAbility().addEffect(effect);
        effect = new SacrificeAllEffect(xValue, new FilterControlledCreaturePermanent("creatures"));
        effect.setText(", sacrifices X creatures");
        this.getSpellAbility().addEffect(effect);
        effect = new SacrificeAllEffect(xValue, new FilterControlledLandPermanent("lands"));
        effect.setText("then sacrifices X lands");
        this.getSpellAbility().addEffect(effect);
    }

    public DeathCloud(final DeathCloud card) {
        super(card);
    }

    @Override
    public DeathCloud copy() {
        return new DeathCloud(this);
    }
}
