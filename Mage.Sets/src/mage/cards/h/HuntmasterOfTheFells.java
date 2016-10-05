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
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.r.RavagerOfTheFells;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author BetaSteward
 */
public class HuntmasterOfTheFells extends CardImpl {

    public HuntmasterOfTheFells(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add("Human");
        this.subtype.add("Werewolf");

        this.transformable = true;
        this.secondSideCardClazz = RavagerOfTheFells.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature enters the battlefield or transforms into Huntmaster of the Fells, put a 2/2 green Wolf creature token onto the battlefield and you gain 2 life.
        this.addAbility(new HuntmasterOfTheFellsAbility());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Huntmaster of the Fells.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.getInstance(), TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public HuntmasterOfTheFells(final HuntmasterOfTheFells card) {
        super(card);
    }

    @Override
    public HuntmasterOfTheFells copy() {
        return new HuntmasterOfTheFells(this);
    }
}

class HuntmasterOfTheFellsAbility extends TriggeredAbilityImpl {

    public HuntmasterOfTheFellsAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WolfToken()), false);
        this.addEffect(new GainLifeEffect(2));
    }

    public HuntmasterOfTheFellsAbility(final HuntmasterOfTheFellsAbility ability) {
        super(ability);
    }

    @Override
    public HuntmasterOfTheFellsAbility copy() {
        return new HuntmasterOfTheFellsAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TRANSFORMED && event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && !permanent.isTransformed()) {
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature enters the battlefield or transforms into {this}, put a 2/2 green Wolf creature token onto the battlefield and you gain 2 life.";
    }
}
