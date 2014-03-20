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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public class PunishIgnorance extends CardImpl<PunishIgnorance> {

    public PunishIgnorance(UUID ownerId) {
        super(ownerId, 183, "Punish Ignorance", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{W}{U}{U}{B}");
        this.expansionSetCode = "ALA";

        this.color.setWhite(true);
        this.color.setBlue(true);
        this.color.setBlack(true);

        // Counter target spell. Its controller loses 3 life and you gain 3 life.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterSpell()));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(3));
        Effect effect = new GainLifeEffect(3);
        effect.setText("and you gain 3 life");
        this.getSpellAbility().addEffect(effect);
    }

    public PunishIgnorance(final PunishIgnorance card) {
        super(card);
    }

    @Override
    public PunishIgnorance copy() {
        return new PunishIgnorance(this);
    }
}
