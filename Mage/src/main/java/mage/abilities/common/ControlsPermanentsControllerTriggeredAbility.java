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

import mage.constants.ComparisonType;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * State triggered ability that triggers if the ability controller controlls the
 * defined number of permanents.
 *
 * @author LevelX2
 */
public class ControlsPermanentsControllerTriggeredAbility extends StateTriggeredAbility {

    protected final FilterPermanent filter;
    protected final ComparisonType type;
    protected final int value;

    public ControlsPermanentsControllerTriggeredAbility(FilterPermanent filter, ComparisonType type, int value, Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter;
        this.value = value;
        this.type = type;
    }

    public ControlsPermanentsControllerTriggeredAbility(final ControlsPermanentsControllerTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.type = ability.type;
        this.value = ability.value;
    }

    @Override
    public ControlsPermanentsControllerTriggeredAbility copy() {
        return new ControlsPermanentsControllerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int inputValue = game.getBattlefield().countAll(filter, getControllerId(), game);
        return ComparisonType.compare(inputValue, type, value);
    }

    @Override
    public String getRule() {
        return "When you control " + filter.getMessage() + ", " + super.getRule();
    }
}
