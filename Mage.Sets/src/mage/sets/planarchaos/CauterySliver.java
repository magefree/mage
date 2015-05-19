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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author anonymous
 */
public class CauterySliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Sliver", "All Slivers");

    public CauterySliver(UUID ownerId) {
        super(ownerId, 154, "Cautery Sliver", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R}{W}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Sliver");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "{1}, Sacrifice this permanent: This permanent deals 1 damage to target creature or player."
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("1"));
        ability1.addCost(new SacrificeSourceCost());
        ability1.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability1, Duration.WhileOnBattlefield, filter,
                        "All Slivers have \"{1}, Sacrifice this permanent: This permanent deals 1 damage to target creature or player.\"")));
        // All Slivers have "{1}, Sacrifice this permanent: Prevent the next 1 damage that would be dealt to target Sliver creature or player this turn."
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new ManaCostsImpl("1"));
        ability2.addCost(new SacrificeSourceCost());
        ability2.addTarget(new TargetSliverCreatureOrPlayer());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability2, Duration.WhileOnBattlefield, filter,
                        "All Slivers have \"{1}, Sacrifice this permanent: Prevent the next 1 damage that would be dealt to target Sliver creature or player this turn.\"")));
    }

    public CauterySliver(final CauterySliver card) {
        super(card);
    }

    @Override
    public CauterySliver copy() {
        return new CauterySliver(this);
    }
}


class TargetSliverCreatureOrPlayer extends TargetCreatureOrPlayer {
    public TargetSliverCreatureOrPlayer(){
        super();
        filter = new FilterCreatureOrPlayerByType("Sliver", "Sliver creature or player");
    }
}

class FilterCreatureOrPlayerByType extends FilterCreatureOrPlayer {
    public FilterCreatureOrPlayerByType (String type, String name) {
        super(name);
        creatureFilter = new FilterCreaturePermanent(type);
    }
}
