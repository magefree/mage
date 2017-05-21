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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class AwokenHorror extends CardImpl {

    public AwokenHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add("Kraken");
        this.subtype.add("Horror");
        this.power = new MageInt(7);
        this.toughness = new MageInt(8);
        this.color.setBlue(true);

        this.nightCard = true;

        // When this creature transforms into Awoken Horrow, return all non-Horror creatures to their owners' hands.
        this.addAbility(new AwokenHorrorAbility());
    }

    public AwokenHorror(final AwokenHorror card) {
        super(card);
    }

    @Override
    public AwokenHorror copy() {
        return new AwokenHorror(this);
    }
}

class AwokenHorrorAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Horror creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.HORROR)));
    }

    public AwokenHorrorAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandFromBattlefieldAllEffect(filter), false);
    }

    public AwokenHorrorAbility(final AwokenHorrorAbility ability) {
        super(ability);
    }

    @Override
    public AwokenHorrorAbility copy() {
        return new AwokenHorrorAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.isTransformed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature transforms into Awoken Horror, return all non-Horror creatures to their owners' hands.";
    }
}
