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
package mage.sets.zendikar;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author North
 * @author LevelX - changed to checkInterveningIfClause 
 */
public class EmeriaTheSkyRuin extends CardImpl<EmeriaTheSkyRuin> {

    public EmeriaTheSkyRuin(UUID ownerId) {
        super(ownerId, 213, "Emeria, the Sky Ruin", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ZEN";

        // Emeria, the Sky Ruin enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // At the beginning of your upkeep, if you control seven or more Plains, you may return target creature card from your graveyard to the battlefield.
        this.addAbility(new EmeriaTheSkyRuinTriggeredAbility());
        // {tap}: Add {W} to your mana pool.
        this.addAbility(new WhiteManaAbility());
    }

    public EmeriaTheSkyRuin(final EmeriaTheSkyRuin card) {
        super(card);
    }

    @Override
    public EmeriaTheSkyRuin copy() {
        return new EmeriaTheSkyRuin(this);
    }
}

class EmeriaTheSkyRuinTriggeredAbility extends TriggeredAbilityImpl<EmeriaTheSkyRuinTriggeredAbility> {

    static final FilterLandPermanent filter = new FilterLandPermanent("Plains");

    static {
        filter.getSubtype().add("Plains");
        filter.setScopeSubtype(ComparisonScope.Any);
    }

    public EmeriaTheSkyRuinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        this.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
    }

    public EmeriaTheSkyRuinTriggeredAbility(final EmeriaTheSkyRuinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EmeriaTheSkyRuinTriggeredAbility copy() {
        return new EmeriaTheSkyRuinTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean checkInterveningIfClause(Game game) {
	return game.getBattlefield().countAll(filter, this.controllerId, game) >= 7;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control seven or more Plains, you may return target creature card from your graveyard to the battlefield.";
    }
}
