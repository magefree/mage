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
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.condition.common.ControlsPermanentCondition.CountType;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth

 */
public class HagHedgeMage extends CardImpl<HagHedgeMage> {
    
    private final static FilterLandPermanent filter = new FilterLandPermanent();
    private final static FilterLandPermanent filter2 = new FilterLandPermanent();
    
    static {
        filter.add(new SubtypePredicate("Swamp"));
        filter2.add(new SubtypePredicate("Forest"));
    }
    
    private String rule = "When {this} enters the battlefield, if you control two or more Swamps, you may have target player discard a card.";
    private String rule2 = "When {this} enters the battlefield, if you control two or more Forests, you may put target card from your graveyard on top of your library.";

    public HagHedgeMage(UUID ownerId) {
        super(ownerId, 123, "Hag Hedge-Mage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B/G}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Hag");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Hag Hedge-Mage enters the battlefield, if you control two or more Swamps, you may have target player discard a card.
        Ability ability = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1), true), new ControlsPermanentCondition(filter, CountType.MORE_THAN, 1), rule, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
        // When Hag Hedge-Mage enters the battlefield, if you control two or more Forests, you may put target card from your graveyard on top of your library.
        Ability ability2 = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true), true), new ControlsPermanentCondition(filter2, CountType.MORE_THAN, 1), rule2, true);
        ability2.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability2);
    }

    public HagHedgeMage(final HagHedgeMage card) {
        super(card);
    }

    @Override
    public HagHedgeMage copy() {
        return new HagHedgeMage(this);
    }
}
