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
package mage.sets.antiquities;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author MarcoMarin
 */
public class Powerleech extends CardImpl {

    public Powerleech(UUID ownerId) {
        super(ownerId, 64, "Powerleech", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");
        this.expansionSetCode = "ATQ";

        // Whenever an artifact an opponent controls becomes tapped or an opponent activates an artifact's ability without {tap} in its activation cost, you gain 1 life.
        this.addAbility(new AbilityActivatedTriggeredAbility3());
    }

    public Powerleech(final Powerleech card) {
        super(card);
    }

    @Override
    public Powerleech copy() {
        return new Powerleech(this);
    }
}
class AbilityActivatedTriggeredAbility3 extends TriggeredAbilityImpl {

    AbilityActivatedTriggeredAbility3() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1));
    }

    AbilityActivatedTriggeredAbility3(final AbilityActivatedTriggeredAbility3 ability) {
        super(ability);
    }

    @Override
    public AbilityActivatedTriggeredAbility3 copy() {
        return new AbilityActivatedTriggeredAbility3(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY ||
               event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent isArtifact = game.getPermanent(event.getSourceId());
        return isArtifact != null && isArtifact.getCardType().contains(CardType.ARTIFACT) &&
                !isArtifact.getControllerId().equals(this.getControllerId());
        
    }

    @Override
    public String getRule() {
        return "Whenever an artifact an opponent controls becomes tapped or an opponent activates an artifact's ability without {tap} in its activation cost, you gain 1 life.";
    }
}
