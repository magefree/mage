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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.common.SimpleActivatedAbility;
import mage.Constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.players.Player;
import mage.abilities.effects.OneShotEffect;

/**
 *
 * @author jeffwadsworth
 */
public class NirkanaRevenant extends CardImpl<NirkanaRevenant> {

    public NirkanaRevenant(UUID ownerId) {
        super(ownerId, 120, "Nirkana Revenant", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Vampire");
        this.subtype.add("Shade");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you tap a Swamp for mana, add {B} to your mana pool.
        this.addAbility(new NirkanaRevenantTriggeredAbility());
        
        // {B}: Nirkana Revenant gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Constants.Duration.EndOfTurn), new ManaCostsImpl("{B}")));
    }

    public NirkanaRevenant(final NirkanaRevenant card) {
        super(card);
    }

    @Override
    public NirkanaRevenant copy() {
        return new NirkanaRevenant(this);
    }
}

  class NirkanaRevenantTriggeredAbility extends TriggeredAbilityImpl<NirkanaRevenantTriggeredAbility> {
      
    public NirkanaRevenantTriggeredAbility() {
	super(Zone.BATTLEFIELD, new NirkanaRevenantEffect());
    }

    public NirkanaRevenantTriggeredAbility(final NirkanaRevenantTriggeredAbility ability) {
	super(ability);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
	if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA ) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent == null) {
		permanent = (Permanent) game.getLastKnownInformation(event.getSourceId(), Zone.BATTLEFIELD);
            }
            if (permanent != null && permanent.getSubtype().contains("Swamp") && permanent.getControllerId().equals(this.controllerId)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                return true;
            }
	}
	return false;
    }
    
    @Override
    public NirkanaRevenantTriggeredAbility copy() {
	return new NirkanaRevenantTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a Swamp for mana, add B to your mana pool (in addition to the mana the land produces).";
    }
}

class NirkanaRevenantEffect extends OneShotEffect<NirkanaRevenantEffect> {
    
    NirkanaRevenantEffect() {
        super(Outcome.PutManaInPool);
    }
    
    NirkanaRevenantEffect(final NirkanaRevenantEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addMana(Mana.BlackMana, game, source);
            return true;
        }
        return false;
    }
    
    @Override
    public NirkanaRevenantEffect copy() {
	return new NirkanaRevenantEffect(this);
    }
}


