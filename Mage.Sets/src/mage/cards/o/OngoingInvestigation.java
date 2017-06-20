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
package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class OngoingInvestigation extends CardImpl {

    public OngoingInvestigation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // Whenever one or more creatures you control deal combat damage to a player, investigate.
        this.addAbility(new OngoingInvestigationTriggeredAbility());
        
        // {1}{G}, Exile a creature card from your graveyard: Investigate. You gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new InvestigateEffect(), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard("a creature card from your graveyard"))));
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);
    }

    public OngoingInvestigation(final OngoingInvestigation card) {
        super(card);
    }

    @Override
    public OngoingInvestigation copy() {
        return new OngoingInvestigation(this);
    }
}

class OngoingInvestigationTriggeredAbility extends TriggeredAbilityImpl {

    private boolean madeDamage = false;
    private Set<UUID> damagedPlayers = new HashSet<>();

    public OngoingInvestigationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InvestigateEffect(), false);
    }

    public OngoingInvestigationTriggeredAbility(final OngoingInvestigationTriggeredAbility ability) {
        super(ability);
        this.madeDamage = ability.madeDamage;
        this.damagedPlayers = new HashSet<>();
        this.damagedPlayers.addAll(ability.damagedPlayers);
    }

    @Override
    public OngoingInvestigationTriggeredAbility copy() {
        return new OngoingInvestigationTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER || event.getType() == EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getControllerId().equals(this.getControllerId())) {
                madeDamage = true;
                damagedPlayers.add(event.getPlayerId());
            }
        }
        if (event.getType() == EventType.COMBAT_DAMAGE_STEP_POST) {
            if (madeDamage) {
                Set<UUID> damagedPlayersCopy = new HashSet<>();
                damagedPlayersCopy.addAll(damagedPlayers);
                for(Effect effect: this.getEffects()) {
                    effect.setValue("damagedPlayers", damagedPlayersCopy);
                }
                damagedPlayers.clear();
                madeDamage = false;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control deal combat damage to a player, " + super.getRule();
    }
}
