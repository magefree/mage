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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KithkinToken;

/**
 *
 * @author Styxo
 */
public class MilitiasPride extends CardImpl {

    public MilitiasPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add("Kithkin");

        // Whenever a creature you control attacks, you may pay {W}. If you do create a 1/1 white Kithkin Soldier creature token in play tapped and attacking
        this.addAbility(new MilitiasPrideTriggerAbility());

    }

    public MilitiasPride(final MilitiasPride card) {
        super(card);
    }

    @Override
    public MilitiasPride copy() {
        return new MilitiasPride(this);
    }
}

class MilitiasPrideTriggerAbility extends TriggeredAbilityImpl {

    public MilitiasPrideTriggerAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CreateTokenEffect(new KithkinToken(), 1, true, true), new ManaCostsImpl("{W}")));
    }

    public MilitiasPrideTriggerAbility(final MilitiasPrideTriggerAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        filter.add(Predicates.not(new TokenPredicate()));
        Permanent permanent = (Permanent) game.getPermanent(event.getSourceId());
        return permanent != null && filter.match(permanent, sourceId, controllerId, game);
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature you control attacks, you may pay {W}. If you do, create a 1/1 white Kithkin Soldier creature token that's tapped and attacking.";
    }

    @Override
    public MilitiasPrideTriggerAbility copy() {
        return new MilitiasPrideTriggerAbility(this);
    }
}
