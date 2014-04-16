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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class TillingTreefolk extends CardImpl<TillingTreefolk> {
    
    public TillingTreefolk(UUID ownerId) {
        super(ownerId, 78, "Tilling Treefolk", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Treefolk");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Tilling Treefolk enters the battlefield, you may return up to two target land cards from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("you may return up to two target land cards from your graveyard to your hand");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        ability.addTarget(new TargetCardInYourGraveyard(0, 2, new FilterLandCard()));
        this.addAbility(ability);
        
    }

    public TillingTreefolk(final TillingTreefolk card) {
        super(card);
    }

    @Override
    public TillingTreefolk copy() {
        return new TillingTreefolk(this);
    }
}
