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
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author jeffwadsworth

 */
public class SelkieHedgeMage extends CardImpl<SelkieHedgeMage> {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("Forests");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("Islands");
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(new SubtypePredicate("Forests"));
        filter2.add(new SubtypePredicate("Islands"));
        filter3.add(new TappedPredicate());
    }
    
    private String rule1 = "When {this} enters the battlefield, if you control two or more Forests, you may gain 3 life.";
    private String rule2 = "When {this} enters the battlefield, if you control two or more Islands, you may return target tapped creature to its owner's hand.";

    public SelkieHedgeMage(UUID ownerId) {
        super(ownerId, 158, "Selkie Hedge-Mage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G/U}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Selkie Hedge-Mage enters the battlefield, if you control two or more Forests, you may gain 3 life.
        Ability ability = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3), true), new ControlsPermanentCondition(filter, CountType.MORE_THAN, 1), rule1);
        this.addAbility(ability);
        
        // When Selkie Hedge-Mage enters the battlefield, if you control two or more Islands, you may return target tapped creature to its owner's hand.
        Ability ability2 = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true), new ControlsPermanentCondition(filter2, CountType.MORE_THAN, 1), rule2);
        ability2.addTarget(new TargetPermanent(filter3));
        this.addAbility(ability2);
        
    }

    public SelkieHedgeMage(final SelkieHedgeMage card) {
        super(card);
    }

    @Override
    public SelkieHedgeMage copy() {
        return new SelkieHedgeMage(this);
    }
}
