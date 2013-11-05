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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.SkipUntapSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth

 */
public class AltarGolem extends CardImpl<AltarGolem> {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures");
    
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public AltarGolem(UUID ownerId) {
        super(ownerId, 166, "Altar Golem", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Golem");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Altar Golem's power and toughness are each equal to the number of creatures on the battlefield.
        DynamicValue amount = new PermanentsOnBattlefieldCount(new FilterCreaturePermanent("creatures in play"));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(amount, Duration.WhileOnBattlefield)));
        
        // Altar Golem doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUntapSourceEffect()));
        
        // Tap five untapped creatures you control: Untap Altar Golem.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(5, 5, filter, true))));
        
    }

    public AltarGolem(final AltarGolem card) {
        super(card);
    }

    @Override
    public AltarGolem copy() {
        return new AltarGolem(this);
    }
}
