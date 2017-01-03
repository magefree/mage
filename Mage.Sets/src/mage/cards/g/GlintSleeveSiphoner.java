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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public class GlintSleeveSiphoner extends CardImpl {

    public GlintSleeveSiphoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Glint-Sleeve Siphoner enters the battlefield or attacks, you get {E}.
        this.addAbility(new GlintSleeveSiphonerAbility());

        // At the beginning of your upkeep, you may pay {E}{E}. If you do, draw a card and you lose 1 life.
        DoIfCostPaid doIfCostPaidEffect = new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new PayEnergyCost(2));
        doIfCostPaidEffect.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, doIfCostPaidEffect, TargetController.YOU, false, false,
                "At the beginning of your upkeep, you may pay {E}{E}. If you do, draw a card and you lose 1 life."));
    }

    public GlintSleeveSiphoner(final GlintSleeveSiphoner card) {
        super(card);
    }

    @Override
    public GlintSleeveSiphoner copy() {
        return new GlintSleeveSiphoner(this);
    }
}

class GlintSleeveSiphonerAbility extends TriggeredAbilityImpl {

    public GlintSleeveSiphonerAbility() {
        super(Zone.BATTLEFIELD, new GetEnergyCountersControllerEffect(1), false);
    }

    public GlintSleeveSiphonerAbility(final GlintSleeveSiphonerAbility ability) {
        super(ability);
    }

    @Override
    public GlintSleeveSiphonerAbility copy() {
        return new GlintSleeveSiphonerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED || event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or attacks, target creature you control gains double strike and lifelink until end of turn";
    }
}
