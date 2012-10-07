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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.effects.common.PopulateEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevleX2
 */
public class RootbornDefenses extends CardImpl<RootbornDefenses> {

    public RootbornDefenses(UUID ownerId) {
        super(ownerId, 19, "Rootborn Defenses", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "RTR";

        this.color.setWhite(true);

        // Populate. Creatures you control are indestructible this turn. 
        // (To populate, put a token onto the battlefield that's a copy of a creature 
        // token you control. Damage and effects that say "destroy" don't destroy 
        // indestructible creatures.)
        this.getSpellAbility().addEffect(new PopulateEffect());
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(new IndestructibleAbility(), Duration.EndOfTurn, new FilterControlledCreaturePermanent(),"Creatures you control are indestructible this turn"));
    }

    public RootbornDefenses(final RootbornDefenses card) {
        super(card);
    }

    @Override
    public RootbornDefenses copy() {
        return new RootbornDefenses(this);
    }
}

