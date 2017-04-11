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
package mage.cards.o;

import mage.constants.ComparisonType;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class OjutaisCommand extends CardImpl {
    
    private static final FilterCard filter = new FilterCreatureCard("creature card with converted mana cost 2 or less from your graveyard");
    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
    }
    
    private static final FilterSpell filter2 = new FilterSpell("creature spell");

    static {
        filter2.add(new CardTypePredicate(CardType.CREATURE));
    }
    
    public OjutaisCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{U}");

        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        
        // Return target creature card with converted mana cost 2 or less from your graveyard to the battlefield;
        this.getSpellAbility().getEffects().add(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().getTargets().add(new TargetCardInYourGraveyard(filter));
        
        // or You gain 4 life;
        Mode mode = new Mode();
        mode.getEffects().add(new GainLifeEffect(4));
        this.getSpellAbility().getModes().addMode(mode);
        
        // or Counter target creature spell;
        mode = new Mode();
        mode.getTargets().add(new TargetSpell(filter2));
        mode.getEffects().add(new CounterTargetEffect());
        this.getSpellAbility().getModes().addMode(mode);        
        
        // or Draw a card
        mode = new Mode();
        mode.getEffects().add(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().getModes().addMode(mode);
    }

    public OjutaisCommand(final OjutaisCommand card) {
        super(card);
    }

    @Override
    public OjutaisCommand copy() {
        return new OjutaisCommand(this);
    }
}
