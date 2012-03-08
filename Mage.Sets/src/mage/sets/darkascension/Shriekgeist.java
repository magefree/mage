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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class Shriekgeist extends CardImpl<Shriekgeist> {

    public Shriekgeist(UUID ownerId) {
        super(ownerId, 49, "Shriekgeist", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Shriekgeist deals combat damage to a player, that player puts the top two cards of his or her library into his or her graveyard.
        this.addAbility(new ShriekgeistTriggeredAbility());
    }

    public Shriekgeist(final Shriekgeist card) {
        super(card);
    }

    @Override
    public Shriekgeist copy() {
        return new Shriekgeist(this);
    }
}

class ShriekgeistTriggeredAbility extends TriggeredAbilityImpl<ShriekgeistTriggeredAbility> {

    public ShriekgeistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(2));
    }

    public ShriekgeistTriggeredAbility(final ShriekgeistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShriekgeistTriggeredAbility copy() {
        return new ShriekgeistTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, that player puts the top two cards of his or her library into his or her graveyard.";
    }
}
