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
package mage.sets.seventhedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Quercitron
 */
public class UktabiWildcats extends CardImpl<UktabiWildcats> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forests you control");
    private static final FilterControlledPermanent sacrificeFilter = new FilterControlledPermanent("a Forest");

    static {
        filter.add(new SubtypePredicate("Forest"));
        sacrificeFilter.add(new SubtypePredicate("Forest"));
    }
    
    public UktabiWildcats(UUID ownerId) {
        super(ownerId, 278, "Uktabi Wildcats", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.expansionSetCode = "7ED";
        this.subtype.add("Cat");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Uktabi Wildcats's power and toughness are each equal to the number of Forests you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
        
        // {G}, Sacrifice a Forest: Regenerate Uktabi Wildcats.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new  RegenerateSourceEffect(), new ManaCostsImpl("{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, sacrificeFilter, true)));
        this.addAbility(ability);
    }

    public UktabiWildcats(final UktabiWildcats card) {
        super(card);
    }

    @Override
    public UktabiWildcats copy() {
        return new UktabiWildcats(this);
    }
}
