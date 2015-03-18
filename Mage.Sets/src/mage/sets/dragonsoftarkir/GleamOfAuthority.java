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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GleamOfAuthority extends CardImpl {

    public GleamOfAuthority(UUID ownerId) {
        super(ownerId, 19, "Gleam of Authority", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature gets +1/+1 for each +1/+1 counter on other creatures you control
        DynamicValue amount = new CountersOnControlledCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(amount, amount, Duration.WhileOnBattlefield)));        
        
        // Enchanted creature has vigilance and "{W}, {T}: Bloster 1."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA)));
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BolsterEffect(1), new ManaCostsImpl("{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.AURA)));        
    }

    public GleamOfAuthority(final GleamOfAuthority card) {
        super(card);
    }

    @Override
    public GleamOfAuthority copy() {
        return new GleamOfAuthority(this);
    }
}

class CountersOnControlledCount implements DynamicValue {

    static FilterCreaturePermanent filter = new FilterCreaturePermanent();
    
    public CountersOnControlledCount() {
    }

    public CountersOnControlledCount(final CountersOnControlledCount dynamicValue) {
        super();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(filter, sourceAbility.getControllerId(), game)) {
            if (!permanent.getId().equals(sourceAbility.getSourceId())) {
                count += permanent.getCounters().getCount(CounterType.P1P1);
            }
        }
        return count;
    }

    @Override
    public DynamicValue copy() {
        return new CountersOnControlledCount(this);
    }

    @Override
    public String getMessage() {
        return "+1/+1 counter on other creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
