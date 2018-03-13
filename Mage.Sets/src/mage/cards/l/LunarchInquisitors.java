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
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public class LunarchInquisitors extends CardImpl {

    private static final String rule = "Whenever this creature transforms into Lunarch Inquisitors, you may exile another target creature until Lunarch Inquisitors leaves the battlefield";

    public LunarchInquisitors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // When this creature transforms into Lunarch Inquisitors, you may exile another target creature until Lunarch Inquisitors leaves the battlefield.
        Ability ability = new LunarchInquisitorsAbility();
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    public LunarchInquisitors(final LunarchInquisitors card) {
        super(card);
    }

    @Override
    public LunarchInquisitors copy() {
        return new LunarchInquisitors(this);
    }
}

class LunarchInquisitorsAbility extends TriggeredAbilityImpl {

    public LunarchInquisitorsAbility() {
        super(Zone.BATTLEFIELD, new LunarchInquisitorsExileEffect(), true);
        // Rule only shown on the night side
        this.setRuleVisible(false);
    }

    public LunarchInquisitorsAbility(final LunarchInquisitorsAbility ability) {
        super(ability);
    }

    @Override
    public LunarchInquisitorsAbility copy() {
        return new LunarchInquisitorsAbility(this);
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
        return "Whenever this creature transforms into Lunarch Inquisitors, you may exile another target creature until Lunarch Inquisitors leaves the battlefield.";
    }
}

class LunarchInquisitorsExileEffect extends OneShotEffect {

    public LunarchInquisitorsExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature until {this} leaves the battlefield";
    }

    public LunarchInquisitorsExileEffect(final LunarchInquisitorsExileEffect effect) {
        super(effect);
    }

    @Override
    public LunarchInquisitorsExileEffect copy() {
        return new LunarchInquisitorsExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If Lunarch Inquisitors leaves the battlefield before its triggered ability resolves,
        // the target won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
