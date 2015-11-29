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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public class GridMonitor extends CardImpl {

    public GridMonitor(UUID ownerId) {
        super(ownerId, 183, "Grid Monitor", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Construct");
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // You can't cast creature spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GridMonitorEffect()));
    }

    public GridMonitor(final GridMonitor card) {
        super(card);
    }

    @java.lang.Override
    public GridMonitor copy() {
        return new GridMonitor(this);
    }
}

class GridMonitorEffect extends ContinuousRuleModifyingEffectImpl {
    
    public GridMonitorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You can't cast creature spells";
    }

    public GridMonitorEffect(final GridMonitorEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public GridMonitorEffect copy() {
        return new GridMonitorEffect(this);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @java.lang.Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL && event.getPlayerId().equals(source.getControllerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }
}