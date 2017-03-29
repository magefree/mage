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
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class DuelistsHeritage extends CardImpl {

    public DuelistsHeritage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");


        // Whenever one or more creatures attack, you may have target attacking creature gain double strike until end of turn.
        Ability ability = new DuelistsHeritageTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(new FilterAttackingCreature()));
        this.addAbility(ability);
    }

    public DuelistsHeritage(final DuelistsHeritage card) {
        super(card);
    }

    @Override
    public DuelistsHeritage copy() {
        return new DuelistsHeritage(this);
    }
}

class DuelistsHeritageTriggeredAbility extends TriggeredAbilityImpl {

    public DuelistsHeritageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), true);
    }

    public DuelistsHeritageTriggeredAbility(final DuelistsHeritageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DuelistsHeritageTriggeredAbility copy() {
        return new DuelistsHeritageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !game.getCombat().getAttackers().isEmpty();
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures attack, " + super.getRule();
    }
}
