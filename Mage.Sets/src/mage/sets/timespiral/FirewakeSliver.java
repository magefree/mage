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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class FirewakeSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All sliver creatures");
    private static final FilterCreaturePermanent targetSliverFilter = new FilterCreaturePermanent("Sliver");

    static {
        filter.add(new SubtypePredicate("Sliver"));
        targetSliverFilter.add(new SubtypePredicate("Sliver"));
    }
    
    public FirewakeSliver(UUID ownerId) {
        super(ownerId, 238, "Firewake Sliver", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Sliver");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Sliver creatures have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)));        
        
        // All Slivers have "{1}, Sacrifice this permanent: Target Sliver creature gets +2/+2 until end of turn."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2,2,Duration.EndOfTurn), new GenericManaCost(1));
        gainedAbility.addCost(new SacrificeSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent(targetSliverFilter));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                gainedAbility, Duration.WhileOnBattlefield,
                filter, "All Slivers have \"{1}, Sacrifice this permanent: Target Sliver creature gets +2/+2 until end of turn.\"")));                
        
    }

    public FirewakeSliver(final FirewakeSliver card) {
        super(card);
    }

    @Override
    public FirewakeSliver copy() {
        return new FirewakeSliver(this);
    }
}
