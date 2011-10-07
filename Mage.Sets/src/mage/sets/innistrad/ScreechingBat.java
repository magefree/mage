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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class ScreechingBat extends CardImpl<ScreechingBat> {

    public ScreechingBat(UUID ownerId) {
        super(ownerId, 114, "Screeching Bat", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Bat");

        this.canTransform = true;
        this.secondSideCard = new StalkingVampire(ownerId);

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, you may pay {2}{B}{B}. If you do, transform Screeching Bat.
        this.addAbility(new TransformAbility());
        this.addAbility(new ScreechingBatBeginningOfUpkeepTriggeredAbility());
    }

    public ScreechingBat(final ScreechingBat card) {
        super(card);
    }

    @Override
    public ScreechingBat copy() {
        return new ScreechingBat(this);
    }
}

class ScreechingBatBeginningOfUpkeepTriggeredAbility extends TriggeredAbilityImpl<ScreechingBatBeginningOfUpkeepTriggeredAbility> {

    public ScreechingBatBeginningOfUpkeepTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new ScreechingBatTransformSourceEffect(), true);
    }

    public ScreechingBatBeginningOfUpkeepTriggeredAbility(final ScreechingBatBeginningOfUpkeepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScreechingBatBeginningOfUpkeepTriggeredAbility copy() {
        return new ScreechingBatBeginningOfUpkeepTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            return event.getPlayerId().equals(this.controllerId);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, you may pay {2}{B}{B}. If you do, transform {this}.";
    }
}

class ScreechingBatTransformSourceEffect extends OneShotEffect<ScreechingBatTransformSourceEffect> {

    public ScreechingBatTransformSourceEffect() {
        super(Constants.Outcome.Transform);
        staticText = "transform {this}";
    }

    public ScreechingBatTransformSourceEffect(final ScreechingBatTransformSourceEffect effect) {
        super(effect);
    }

    @Override
    public ScreechingBatTransformSourceEffect copy() {
        return new ScreechingBatTransformSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Cost cost = new ManaCostsImpl("{2}{B}{B}");
            if (cost.pay(source, game, permanent.getControllerId(), permanent.getControllerId(), false)) {
                if (permanent.canTransform()) {
                    permanent.setTransformed(!permanent.isTransformed());
                }
            }
            return true;
        }
        return false;
    }

}
