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
package mage.sets.portalthreekingdoms;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public class XiahouDunTheOneEyed extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("a black card");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public XiahouDunTheOneEyed(UUID ownerId) {
        super(ownerId, 92, "Xiahou Dun, the One-Eyed", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "PTK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Horsemanship
        // Sacrifice Xiahou Dun, the One-Eyed: Return target black card from your graveyard to your hand. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, 
                new ReturnFromGraveyardToHandTargetEffect(), new SacrificeSourceCost(), MyTurnBeforeAttackersDeclaredCondition.getInstance());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public XiahouDunTheOneEyed(final XiahouDunTheOneEyed card) {
        super(card);
    }

    @Override
    public XiahouDunTheOneEyed copy() {
        return new XiahouDunTheOneEyed(this);
    }
}
