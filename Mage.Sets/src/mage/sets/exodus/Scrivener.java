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
package mage.sets.exodus;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author cbt33, BetaSteward (GraveDigger)
 */
 
public class Scrivener extends CardImpl<Scrivener> {

    private static final FilterCard filter = new FilterCard("Instant");
    
    static{
    filter.add(new CardTypePredicate(CardType.INSTANT));
    }       
    
    public Scrivener(UUID ownerId) {
        super(ownerId, 46, "Scrivener", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "EXO";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Scrivener enters the battlefield, you may return target instant card from your graveyard to your hand.
       Ability ability = new EntersBattlefieldAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
       ability.addTarget(new TargetCardInGraveyard(filter));
       this.addAbility(ability);
        
    }

    public Scrivener(final Scrivener card) {
        super(card);
    }

    @Override
    public Scrivener copy() {
        return new Scrivener(this);
    }
}
