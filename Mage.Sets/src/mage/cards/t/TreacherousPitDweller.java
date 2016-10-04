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
package mage.sets.avacynrestored;

import mage.constants.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author noxx
 */
public class TreacherousPitDweller extends CardImpl {

    public TreacherousPitDweller(UUID ownerId) {
        super(ownerId, 121, "Treacherous Pit-Dweller", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Demon");

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Undying
        this.addAbility(new UndyingAbility());

        // When Treacherous Pit-Dweller enters the battlefield from a graveyard, target opponent gains control of it.
        this.addAbility(new TreacherousPitDwellerTriggeredAbility());
    }

    public TreacherousPitDweller(final TreacherousPitDweller card) {
        super(card);
    }

    @Override
    public TreacherousPitDweller copy() {
        return new TreacherousPitDweller(this);
    }
}

class TreacherousPitDwellerTriggeredAbility extends TriggeredAbilityImpl {

    private static final String ruleText = "When {this} enters the battlefield from a graveyard, ";

    public TreacherousPitDwellerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TreacherousPitDwellerEffect(),false);
        addTarget(new TargetOpponent());
    }

    public TreacherousPitDwellerTriggeredAbility(final TreacherousPitDwellerTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return  event.getTargetId().equals(getSourceId()) && ((EntersTheBattlefieldEvent) event).getFromZone().equals(Zone.GRAVEYARD);
    }    
    
    @Override
    public TreacherousPitDwellerTriggeredAbility copy() {
        return new TreacherousPitDwellerTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText + super.getRule();
    }
    
}

class TreacherousPitDwellerEffect extends ContinuousEffectImpl {

    public TreacherousPitDwellerEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    public TreacherousPitDwellerEffect(final TreacherousPitDwellerEffect effect) {
        super(effect);
    }

    @Override
    public TreacherousPitDwellerEffect copy() {
        return new TreacherousPitDwellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (permanent != null && targetOpponent != null) {
            return permanent.changeControllerId(targetOpponent.getId(), game);
        } else {
            discard();
        }
        return false;
    }

}
