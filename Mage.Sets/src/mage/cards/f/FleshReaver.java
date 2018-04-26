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
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class FleshReaver extends CardImpl {

    public FleshReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Flesh Reaver deals damage to a creature or opponent, Flesh Reaver deals that much damage to you.
        this.addAbility(new FleshReaverTriggeredAbility());
    }

    public FleshReaver(final FleshReaver card) {
        super(card);
    }

    @Override
    public FleshReaver copy() {
        return new FleshReaver(this);
    }
}

class FleshReaverTriggeredAbility extends TriggeredAbilityImpl {

    FleshReaverTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FleshReaverEffect());
    }

    FleshReaverTriggeredAbility(final FleshReaverTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public FleshReaverTriggeredAbility copy() {
        return new FleshReaverTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(sourceId)) {
            return false;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (event.getTargetId().equals(controllerId)) {
                return false;
            }
        }
        for (Effect effect : this.getEffects()) {
            effect.setValue("damage", event.getAmount());
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to a creature or opponent, {this} deals that much damage to you.";
    }

}

class FleshReaverEffect extends OneShotEffect {

    FleshReaverEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals that much damage to you.";
    }

    FleshReaverEffect(final FleshReaverEffect effect) {
        super(effect);
    }

    @Override
    public FleshReaverEffect copy() {
        return new FleshReaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (creature == null || controller == null) {
            return false;
        }
        int damageToDeal = (Integer) getValue("damage");
        if (damageToDeal > 0) {
            controller.damage(damageToDeal, source.getSourceId(), game, false, true);
        }
        return true;
    }
}
