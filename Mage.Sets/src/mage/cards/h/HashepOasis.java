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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ciaccona007
 */
public class HashepOasis extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Desert");

    static {
        filter.add(new SubtypePredicate(SubType.DESERT));
    }
    public HashepOasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.subtype.add("Desert");

        // {t}: Add {C} to your mana pool.
        addAbility(new ColorlessManaAbility());
        
        // {t}, Pay 1 life: Add {G} to your mana pool.
        Ability ability = new GreenManaAbility();
        ability.addCost(new PayLifeCost(1));
        addAbility(ability);
        
        // {1}{G}{G}, {t}, Sacrifice a Desert: Target creature gets +3/+3 until end of turn. Activate this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(3,3,Duration.EndOfTurn), new ManaCostsImpl("{1}{G}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        addAbility(ability);
    }

    public HashepOasis(final HashepOasis card) {
        super(card);
    }

    @Override
    public HashepOasis copy() {
        return new HashepOasis(this);
    }
}
