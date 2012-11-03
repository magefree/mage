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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author LevelX2
 */
public class AzoriusCharm extends CardImpl<AzoriusCharm> {

    public AzoriusCharm(UUID ownerId) {
        super(ownerId, 145, "Azorius Charm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}{U}");
        this.expansionSetCode = "RTR";

        this.color.setWhite(true);
        this.color.setBlue(true);

        // Choose one — Creatures you control gain lifelink until end of turn;
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Constants.Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures")));

        // or draw a card;
        Mode mode = new Mode();
        mode.getEffects().add(new DrawCardControllerEffect(1));
        this.getSpellAbility().addMode(mode);

        // or put target attacking or blocking creature on top of its owner's library.
        mode = new Mode();
        mode.getTargets().add(new TargetAttackingOrBlockingCreature());
        mode.getEffects().add(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addMode(mode);
    }

    public AzoriusCharm(final AzoriusCharm card) {
        super(card);
    }

    @Override
    public AzoriusCharm copy() {
        return new AzoriusCharm(this);
    }
}

