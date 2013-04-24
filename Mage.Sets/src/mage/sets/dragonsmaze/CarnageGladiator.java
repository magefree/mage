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

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */


public class CarnageGladiator extends CardImpl<CarnageGladiator> {

    public CarnageGladiator (UUID ownerId) {
        super(ownerId, 61, "Carnage Gladiator", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Skeleton");
        this.subtype.add("Warrior");

        this.color.setBlack(true);
        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever a creature blocks, that creature's controller loses 1 life.
        this.addAbility(new CarnageGladiatorTriggeredAbility());

        // {1}{B}{R}: Renegerate Carnage Gladiator.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(),new ManaCostsImpl("{1}{B}{R}")));


    }

    public CarnageGladiator (final CarnageGladiator card) {
        super(card);
    }

    @Override
    public CarnageGladiator copy() {
        return new CarnageGladiator(this);
    }

}

class CarnageGladiatorTriggeredAbility extends TriggeredAbilityImpl<CarnageGladiatorTriggeredAbility> {

    public CarnageGladiatorTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
    }

    public CarnageGladiatorTriggeredAbility(final CarnageGladiatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CarnageGladiatorTriggeredAbility copy() {
        return new CarnageGladiatorTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            if (blocker != null) {
                getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature blocks, that creature's controller loses 1 life.";
    }
}