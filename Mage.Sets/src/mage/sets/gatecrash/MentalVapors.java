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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class MentalVapors extends CardImpl<MentalVapors> {

    public MentalVapors (UUID ownerId) {
        super(ownerId, 72, "Mental Vapors", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.expansionSetCode = "GTC";

        this.color.setBlack(true);

        // Target player discards a card.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Cipher (Then you may exilce this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.)
        this.getSpellAbility().addEffect(new CipherEffect());

    }

    public MentalVapors(final MentalVapors card) {
        super(card);
    }

    @Override
    public MentalVapors  copy() {
        return new MentalVapors(this);
    }
}
