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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.condition.common.ControlsPermanentCondition.CountType;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class DuergarHedgeMage extends CardImpl<DuergarHedgeMage> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Mountain");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("a Plains");
    private static final FilterPermanent filter3 = new FilterPermanent("enchantment");

    static {
        filter.add(new SubtypePredicate("Mountain"));
        filter2.add(new SubtypePredicate("Plains"));
        filter3.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }
    private String rule1 = "When {this} enters the battlefield, if you control two or more Mountains, you may destroy target artifact.";
    private String rule2 = "When {this} Hedge-Mage enters the battlefield, if you control two or more Plains, you may destroy target enchantment.";

    public DuergarHedgeMage(UUID ownerId) {
        super(ownerId, 195, "Duergar Hedge-Mage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R/W}");
        this.expansionSetCode = "CMD";
        this.subtype.add("Dwarf");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Duergar Hedge-Mage enters the battlefield, if you control two or more Mountains, you may destroy target artifact.
        Ability ability = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true), new ControlsPermanentCondition(filter, CountType.MORE_THAN, 1), rule1);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // When Duergar Hedge-Mage enters the battlefield, if you control two or more Plains, you may destroy target enchantment.
        Ability ability2 = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true), new ControlsPermanentCondition(filter2, CountType.MORE_THAN, 1), rule2);
        ability2.addTarget(new TargetPermanent(filter3));
        this.addAbility(ability2);

    }

    public DuergarHedgeMage(final DuergarHedgeMage card) {
        super(card);
    }

    @Override
    public DuergarHedgeMage copy() {
        return new DuergarHedgeMage(this);
    }
}
