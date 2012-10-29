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
package mage.sets.exodus;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class CityOfTraitors extends CardImpl<CityOfTraitors> {

    public CityOfTraitors(UUID ownerId) {
        super(ownerId, 143, "City of Traitors", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "EXO";

        // When you play another land, sacrifice City of Traitors.
        this.addAbility(new CityOfTraitorsTriggeredAbility());

        // {tap}: Add {2} to your mana pool.
        this.addAbility(new SimpleManaAbility(Constants.Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));
    }

    public CityOfTraitors(final CityOfTraitors card) {
        super(card);
    }

    @Override
    public CityOfTraitors copy() {
        return new CityOfTraitors(this);
    }
}

class CityOfTraitorsTriggeredAbility extends TriggeredAbilityImpl<CityOfTraitorsTriggeredAbility> {

    CityOfTraitorsTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    CityOfTraitorsTriggeredAbility(CityOfTraitorsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LAND_PLAYED) {
            Permanent land = game.getPermanent(event.getTargetId());
            if (land.getCardType().contains(CardType.LAND)
                    && land.getControllerId().equals(this.controllerId)
                    && event.getTargetId() != this.getSourceId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CityOfTraitorsTriggeredAbility copy() {
        return new CityOfTraitorsTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you play another land, sacrifice {this}";
    }
}
