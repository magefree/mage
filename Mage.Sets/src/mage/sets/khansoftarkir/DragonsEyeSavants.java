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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public class DragonsEyeSavants extends CardImpl {
    
    private final static FilterCard filter = new FilterCard("a blue card in your hand");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public DragonsEyeSavants(UUID ownerId) {
        super(ownerId, 38, "Dragon's Eye Savants", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Morph - Reveal a blue card in your hand.
        this.addAbility(new MorphAbility(this, new RevealTargetFromHandCost(new TargetCardInHand(filter))));
        
        // When Dragon's Eye Savants is turned face up, look at target opponent's hand.
        Effect effect = new LookAtTargetPlayerHandEffect();
        effect.setText("look at target opponent's hand");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public DragonsEyeSavants(final DragonsEyeSavants card) {
        super(card);
    }

    @Override
    public DragonsEyeSavants copy() {
        return new DragonsEyeSavants(this);
    }
}
