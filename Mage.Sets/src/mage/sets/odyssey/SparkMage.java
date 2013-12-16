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
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33, Loki (Ashling the Extinguisher)
 */
public class SparkMage extends CardImpl<SparkMage> {

    public SparkMage(UUID ownerId) {
        super(ownerId, 222, "Spark Mage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Dwarf");
        this.subtype.add("Wizard");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Spark Mage deals combat damage to a player, you may have Spark Mage deal 1 damage to target creature that player controls.
        this.addAbility(new SparkMageTriggeredAbility());
    }

    public SparkMage(final SparkMage card) {
        super(card);
    }

    @Override
    public SparkMage copy() {
        return new SparkMage(this);
    }
}

class SparkMageTriggeredAbility extends TriggeredAbilityImpl<SparkMageTriggeredAbility> {

    public SparkMageTriggeredAbility(){
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
        this.addTarget(new TargetCreaturePermanent());
    }
    
    public SparkMageTriggeredAbility(final SparkMageTriggeredAbility ability) {
        super(ability);
    }
    
@Override
    public SparkMageTriggeredAbility copy() {
        return new SparkMageTriggeredAbility(this);
    }

@Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())){
                Player opponent = game.getPlayer(event.getPlayerId());
                if (opponent != null) {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getName() + " controls");
                    filter.add(new ControllerIdPredicate(opponent.getId()));
                    this.getTargets().clear();
                    this.getTargets().add(new TargetCreaturePermanent(filter));
                    return true;
                }
            }
        }
        return false;
    }

@Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may have {this} deal 1 damage to target creature that player controls.";
    }

}