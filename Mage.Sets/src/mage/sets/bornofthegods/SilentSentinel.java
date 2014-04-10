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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public class SilentSentinel extends CardImpl<SilentSentinel> {

    private static final FilterCard filter = new FilterCard("enchantment card from your graveyard");
    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public SilentSentinel(UUID ownerId) {
        super(ownerId, 26, "Silent Sentinel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Archon");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Silent Sentinel attacks, you may return target enchantment card from your graveyard to the battlefield.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    public SilentSentinel(final SilentSentinel card) {
        super(card);
    }

    @Override
    public SilentSentinel copy() {
        return new SilentSentinel(this);
    }
}
