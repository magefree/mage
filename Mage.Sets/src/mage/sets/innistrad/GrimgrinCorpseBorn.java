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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SkipUntapSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class GrimgrinCorpseBorn extends CardImpl<GrimgrinCorpseBorn> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.setAnother(true);
    }

    public GrimgrinCorpseBorn(UUID ownerId) {
        super(ownerId, 214, "Grimgrin, Corpse-Born", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.expansionSetCode = "ISD";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Warrior");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Grimgrin, Corpse-Born enters the battlefield tapped and doesn't untap during your untap step.
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUntapSourceEffect()));
        // Sacrifice another creature: Untap Grimgrin and put a +1/+1 counter on it.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
        // Whenever Grimgrin attacks, destroy target creature defending player controls, then put a +1/+1 counter on Grimgrin.
        this.addAbility(new GrimgrinCorpseBornAbility());
    }

    public GrimgrinCorpseBorn(final GrimgrinCorpseBorn card) {
        super(card);
    }

    @Override
    public GrimgrinCorpseBorn copy() {
        return new GrimgrinCorpseBorn(this);
    }
}

class GrimgrinCorpseBornAbility extends TriggeredAbilityImpl<GrimgrinCorpseBornAbility> {

    public GrimgrinCorpseBornAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
        this.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    public GrimgrinCorpseBornAbility(final GrimgrinCorpseBornAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defenderId = game.getCombat().getDefendingPlayer(sourceId);
            filter.getControllerId().add(defenderId);
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            target.setRequired(true);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, destroy target creature defending player controls, then put a +1/+1 counter on {this}.";
    }

    @Override
    public GrimgrinCorpseBornAbility copy() {
        return new GrimgrinCorpseBornAbility(this);
    }
}