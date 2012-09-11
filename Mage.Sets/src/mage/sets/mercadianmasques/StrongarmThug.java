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

package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Backfir3
 */
public class StrongarmThug extends CardImpl<StrongarmThug> {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Mercenary card");

    static {
        filter.add(new SubtypePredicate("Mercenary"));
    }

    public StrongarmThug(UUID ownerId) {
		super(ownerId, 165, "Strongarm Thug", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
		this.expansionSetCode = "MMQ";
		this.subtype.add("Human");
		this.subtype.add("Mercenary");
		this.color.setBlack(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);
	
        // When Strongarm Thug enters the battlefield, you may return target Mercenary card from your graveyard to your hand.
		Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public StrongarmThug(final StrongarmThug card) {
        super(card);
    }

    @Override
    public StrongarmThug copy() {
        return new StrongarmThug(this);
    }

}