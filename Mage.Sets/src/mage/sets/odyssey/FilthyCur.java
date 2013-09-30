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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author cbt33
 */
public class FilthyCur extends CardImpl<FilthyCur> {

    public FilthyCur(UUID ownerId) {
        super(ownerId, 136, "Filthy Cur", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Hound");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Filthy Cur is dealt damage, you lose that much life.
        this.addAbility(new DealtDamageLoseLifeTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeSourceEffect(0), false));
        
    }

    public FilthyCur(final FilthyCur card) {
        super(card);
    }

    @Override
    public FilthyCur copy() {
        return new FilthyCur(this);
    }
}

class DealtDamageLoseLifeTriggeredAbility extends TriggeredAbilityImpl<DealtDamageLoseLifeTriggeredAbility> {

    
       public DealtDamageLoseLifeTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
    }

    public DealtDamageLoseLifeTriggeredAbility(final DealtDamageLoseLifeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealtDamageLoseLifeTriggeredAbility copy() {
        return new DealtDamageLoseLifeTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE) {
            if (event.getTargetId().equals(this.sourceId)) {
                game.getPlayer(this.controllerId).loseLife(event.getAmount(), game);
                this.getControllerId();
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, you lose life equal to that damage";
    }
}
