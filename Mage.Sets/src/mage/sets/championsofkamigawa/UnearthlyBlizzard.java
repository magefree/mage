/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 * 
 */
package mage.sets.championsofkamigawa;

import java.util.UUID;


import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Mode;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX
 */
public class UnearthlyBlizzard extends CardImpl<UnearthlyBlizzard> {

    public UnearthlyBlizzard(UUID ownerId) {
        super(ownerId, 196, "Unearthly Blizzard", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Arcane");
        this.color.setRed(true);
        
        Target target = new TargetCreaturePermanent(0,3);
	target.setTargetName("Select up to three creatures that can't block this turn.");
        
        // Up to three target creatures can't block this turn.
        this.getSpellAbility().addEffect(new UnearthlyBlizzardEffect());
	this.getSpellAbility().addTarget(target);

    }

    public UnearthlyBlizzard(final UnearthlyBlizzard card) {
        super(card);
    }

    @Override
    public UnearthlyBlizzard copy() {
        return new UnearthlyBlizzard(this);
    }
}

class UnearthlyBlizzardEffect extends GainAbilityTargetEffect {

    public UnearthlyBlizzardEffect() {
        super(CantBlockAbility.getInstance(), Constants.Duration.EndOfTurn);
        staticText = "Up to three target creatures can't block this turn";
    }

    public UnearthlyBlizzardEffect(final UnearthlyBlizzardEffect effect) {
        super(effect);
    }

    @Override
    public UnearthlyBlizzardEffect copy() {
        return new UnearthlyBlizzardEffect(this);
    }

    @Override
    public String getText(Mode mode) {
            return staticText;
    }
}