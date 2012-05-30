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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonTokenPermanent;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.common.OpponentSacrificesPermanentTriggeredAbility;


/**
 *
 * @author jeffwadsworth
 */
public class ItThatBetrays extends CardImpl<ItThatBetrays> {
    
    FilterNonTokenPermanent filter = new FilterNonTokenPermanent();

    public ItThatBetrays(UUID ownerId) {
        super(ownerId, 7, "It That Betrays", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{12}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Eldrazi");

        this.power = new MageInt(11);
        this.toughness = new MageInt(11);

        this.addAbility(new AnnihilatorAbility(2));
        
        // Whenever an opponent sacrifices a nontoken permanent, put that card onto the battlefield under your control.
        this.addAbility(new OpponentSacrificesPermanentTriggeredAbility(new ReturnToBattlefieldUnderYourControlTargetEffect()));
    }

    public ItThatBetrays(final ItThatBetrays card) {
        super(card);
    }

    @Override
    public ItThatBetrays copy() {
        return new ItThatBetrays(this);
    }
}
