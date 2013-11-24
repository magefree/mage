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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DromarsCharm extends CardImpl<DromarsCharm> {

    public DromarsCharm(UUID ownerId) {
        super(ownerId, 187, "Dromar's Charm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}{U}{B}");
        this.expansionSetCode = "C13";

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setWhite(true);

        // Choose one - You gain 5 life; or counter target spell; or target creature gets -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new GainLifeEffect(5));
        Mode mode = new Mode();
        mode.getEffects().add(new CounterTargetEffect());
        mode.getTargets().add(new TargetSpell());
        this.getSpellAbility().addMode(mode);
        mode = new Mode();
        mode.getEffects().add(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent(true));
        this.getSpellAbility().addMode(mode);
    }

    public DromarsCharm(final DromarsCharm card) {
        super(card);
    }

    @Override
    public DromarsCharm copy() {
        return new DromarsCharm(this);
    }
}
