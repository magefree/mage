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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ControlPermanentCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.counters.common.DevotionCounter;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public class BloodthirstyOgre extends CardImpl<BloodthirstyOgre> {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Demon");
    static {
        filter.getSubtype().add("Demon");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }
    
    public BloodthirstyOgre(UUID ownerId) {
        super(ownerId, 104, "Bloodthirsty Ogre", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Ogre");
        this.subtype.add("Warrior");
        this.subtype.add("Shaman");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {T}: Put a devotion counter on Bloodthirsty Ogre
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(new DevotionCounter()),new TapSourceCost()));
        // {T}: Target creature gets -X/-X until end of turn, where X is the number of devotion counters on Bloodthirsty Ogre. Activate this ability only if you control a Demon.
        CountersCount devotionCounters = new CountersCount(CounterType.DEVOTION, true);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,new BoostTargetEffect(devotionCounters,devotionCounters, Duration.EndOfTurn),new TapSourceCost());
        ability.addCost(new ControlPermanentCost(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public BloodthirstyOgre(final BloodthirstyOgre card) {
        super(card);
    }

    @Override
    public BloodthirstyOgre copy() {
        return new BloodthirstyOgre(this);
    }    
     
}

class IchorExplosionDynamicValue implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Card sourceCard = game.getCard(sourceAbility.getSourceId());
        if (sourceCard != null) {
            for (Object cost: sourceAbility.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    Permanent p = (Permanent) game.getLastKnownInformation(((SacrificeTargetCost) cost).getPermanents().get(0).getId(), Constants.Zone.BATTLEFIELD);
                    return -1 * p.getPower().getValue();
                }
            }
        }
        return 0;
    }

    @Override
    public DynamicValue clone() {
        return this;
    }

    @Override
    public String getMessage() {
        return ", where X is the sacrificed creature's power";
    }

    @Override
    public String toString() {
        return "-X";
    }
}
