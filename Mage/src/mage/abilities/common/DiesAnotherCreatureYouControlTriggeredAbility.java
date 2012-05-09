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
package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * @author noxx
 */
public class DiesAnotherCreatureYouControlTriggeredAbility extends TriggeredAbilityImpl<DiesAnotherCreatureYouControlTriggeredAbility> {

    protected FilterCreaturePermanent filter;
    protected boolean nontoken;

    public DiesAnotherCreatureYouControlTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreaturePermanent());
    }

    public DiesAnotherCreatureYouControlTriggeredAbility(Effect effect, boolean optional, boolean nontoken) {
        this(effect, optional, new FilterCreaturePermanent(), nontoken);
    }
    
    public DiesAnotherCreatureYouControlTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        this(effect, optional, filter, false);
    }

    public DiesAnotherCreatureYouControlTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter, boolean nontoken) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.nontoken = nontoken;
    }

    public DiesAnotherCreatureYouControlTriggeredAbility(DiesAnotherCreatureYouControlTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.nontoken = ability.nontoken;
    }

    @Override
    public DiesAnotherCreatureYouControlTriggeredAbility copy() {
        return new DiesAnotherCreatureYouControlTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {

            UUID sourceId = getSourceId();
            if (game.getPermanent(sourceId) == null) {
                if (game.getLastKnownInformation(sourceId, Constants.Zone.BATTLEFIELD) == null) {
                    return false;
                }
            }

            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Permanent permanent = zEvent.getTarget();

            if (nontoken && permanent instanceof PermanentToken) {
                return false;
            }

            if (permanent != null && permanent.getCardType().contains(Constants.CardType.CREATURE) &&
                    zEvent.isDiesEvent() &&
                    permanent.getControllerId().equals(this.getControllerId()) && filter != null &&
                    filter.match(permanent)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        if (nontoken) {
            return "Whenever another nontoken creature you control dies, " + super.getRule();
        }
        return "Whenever another creature you control dies, " + super.getRule();
    }
}