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
package mage.sets.legends;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class TheAbyss extends CardImpl {

    public TheAbyss(UUID ownerId) {
        super(ownerId, 34, "The Abyss", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "LEG";
        this.supertype.add("World");

        // At the beginning of each player's upkeep, destroy target nonartifact creature that player controls of his or her choice. It can't be regenerated.
        this.addAbility(new TheAbyssTriggeredAbility());
    }

    public TheAbyss(final TheAbyss card) {
        super(card);
    }

    @Override
    public TheAbyss copy() {
        return new TheAbyss(this);
    }
}

class TheAbyssTriggeredAbility extends TriggeredAbilityImpl {
    
    TheAbyssTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(true), false);
    }
    
    TheAbyssTriggeredAbility(final TheAbyssTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public TheAbyssTriggeredAbility copy() {
        return new TheAbyssTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature you control");
                filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
                filter.add(new ControllerIdPredicate(player.getId()));
                Target target = new TargetCreaturePermanent(filter);
                if (target.canChoose(this.getSourceId(), this.getControllerId(), game) && player.chooseTarget(Outcome.DestroyPermanent, target, this, game)) {
                    for (Effect effect: this.getEffects()) {
                        if (effect instanceof DestroyTargetEffect) {
                            effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "At the beginning of each player's upkeep, destroy target nonartifact creature that player controls of his or her choice. It can't be regenerated.";
    }
}
