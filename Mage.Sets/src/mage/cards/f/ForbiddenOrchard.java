/*
 *
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 *
 */
package mage.cards.f;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SpiritToken;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class ForbiddenOrchard extends CardImpl {

    public ForbiddenOrchard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Whenever you tap Forbidden Orchard for mana, create a 1/1 colorless Spirit creature token under target opponent's control.
        this.addAbility(new ForbiddenOrchardTriggeredAbility());
    }

    public ForbiddenOrchard(final ForbiddenOrchard card) {
        super(card);
    }

    @Override
    public ForbiddenOrchard copy() {
        return new ForbiddenOrchard(this);
    }
}

class ForbiddenOrchardTriggeredAbility extends TriggeredAbilityImpl {

    public ForbiddenOrchardTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenTargetEffect(new SpiritToken()));
        this.addTarget(new TargetOpponent());
    }

    public ForbiddenOrchardTriggeredAbility(final ForbiddenOrchardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever you tap {this} for mana, " + super.getRule();
    }

    @Override
    public ForbiddenOrchardTriggeredAbility copy() {
        return new ForbiddenOrchardTriggeredAbility(this);
    }

}
