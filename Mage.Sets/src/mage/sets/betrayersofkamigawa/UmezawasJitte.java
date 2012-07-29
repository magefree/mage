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
package mage.sets.betrayersofkamigawa;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;

/**
 * @author Loki
 */
public class UmezawasJitte extends CardImpl<UmezawasJitte> {

    public UmezawasJitte(UUID ownerId) {
        super(ownerId, 163, "Umezawa's Jitte", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");

        // Whenever equipped creature deals combat damage, put two charge counters on Umezawa's Jitte.
        this.addAbility(new UmezawasJitteAbility());

        // Remove a charge counter from Umezawa's Jitte: Choose one Equipped creature gets +2/+2 until end of turn; or target creature gets -1/-1 until end of turn; or you gain 2 life.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2, Constants.Duration.EndOfTurn), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        Mode mode = new Mode();
        mode.getEffects().add(new BoostTargetEffect(-1, -1, Constants.Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent());
        ability.addMode(mode);
        mode = new Mode();
        mode.getEffects().add(new GainLifeEffect(2));
        ability.addMode(mode);
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(2)));
    }

    public UmezawasJitte(final UmezawasJitte card) {
        super(card);
    }

    @Override
    public UmezawasJitte copy() {
        return new UmezawasJitte(this);
    }
}

class UmezawasJitteAbility extends TriggeredAbilityImpl<UmezawasJitteAbility> {

    public UmezawasJitteAbility() {
        super(Constants.Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(2)));
    }

    public UmezawasJitteAbility(final UmezawasJitteAbility ability) {
        super(ability);
    }

    @Override
    public UmezawasJitteAbility copy() {
        return new UmezawasJitteAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedEvent) {
            Permanent p = game.getPermanent(event.getSourceId());
            if (((DamagedEvent) event).isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage, put two charge counters on {this}.";
    }
}
