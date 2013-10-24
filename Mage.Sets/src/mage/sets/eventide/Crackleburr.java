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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth

 */
public class Crackleburr extends CardImpl<Crackleburr> {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("two untapped red creatures you control");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("two tapped blue creatures you control");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(Predicates.not(new TappedPredicate()));
        
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(new TappedPredicate());
    }

    public Crackleburr(UUID ownerId) {
        super(ownerId, 100, "Crackleburr", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U/R}{U/R}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elemental");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {UR}{UR}, {tap}, Tap two untapped red creatures you control: Crackleburr deals 3 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new ManaCostsImpl("{U/R}{U/R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, true)));
        ability.addTarget(new TargetCreatureOrPlayer(true));
        this.addAbility(ability);
        
        // {UR}{UR}, {untap}, Untap two tapped blue creatures you control: Return target creature to its owner's hand.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl("{U/R}{U/R}"));
        ability2.addCost(new UntapSourceCost());
        ability2.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter2, true)));
        ability2.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability2);
        
    }

    public Crackleburr(final Crackleburr card) {
        super(card);
    }

    @Override
    public Crackleburr copy() {
        return new Crackleburr(this);
    }
}
