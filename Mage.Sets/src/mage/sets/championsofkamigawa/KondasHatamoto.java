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
package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.decorator.ConditionalStaticAbility;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX
 */
public class KondasHatamoto extends CardImpl<KondasHatamoto>{
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Legendary Samurai");

    static {
        filter.getSupertype().add("Legendary");
        filter.setScopeSupertype(Filter.ComparisonScope.Any);
        filter.getSubtype().add("Samurai");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }
    
    public KondasHatamoto (UUID ownerId) {
        super(ownerId, 31, "Konda's Hatamoto", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Human");
        this.subtype.add("Samurai");
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        
        this.addAbility(new BushidoAbility(1));
        
        ConditionalStaticAbility ability = new ConditionalStaticAbility(Zone.BATTLEFIELD,
                        new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield),
                        new ControlsPermanentCondition(filter), 
                        "As long as you control a legendary Samurai, {this} gets +1/+2 and has vigilance.");
        ability.addEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
        
    }

    public KondasHatamoto (final KondasHatamoto card) {
        super(card);
    }

    @Override
    public KondasHatamoto copy() {
        return new KondasHatamoto(this);
    }    
}
