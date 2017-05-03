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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DeathtouchSnakeToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class HapatraVizierOfPoisons extends CardImpl {

    public HapatraVizierOfPoisons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Hapatra, Vizier of Poisons deals combat damage to a player, you may put a -1/-1 counter on target creature.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever you put one or more -1/-1 counters on a creature, create a 1/1 green Snake creature token with deathtouch.
        this.addAbility(new HapatraVizierOfPoisonsTriggeredAbility(new CreateTokenEffect(new DeathtouchSnakeToken()), false));

    }

    public HapatraVizierOfPoisons(final HapatraVizierOfPoisons card) {
        super(card);
    }

    @Override
    public HapatraVizierOfPoisons copy() {
        return new HapatraVizierOfPoisons(this);
    }
}

class HapatraVizierOfPoisonsTriggeredAbility extends TriggeredAbilityImpl {

    public HapatraVizierOfPoisonsTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public HapatraVizierOfPoisonsTriggeredAbility(HapatraVizierOfPoisonsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.M1M1.getName())
                && controllerId.equals(game.getControllerId(event.getSourceId()))) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return (permanent != null
                    && permanent.isCreature());
        }
        return false;

    }

    @Override
    public HapatraVizierOfPoisonsTriggeredAbility copy() {
        return new HapatraVizierOfPoisonsTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you put one or more -1/-1 counters on a creature, " + super.getRule();
    }
}
