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
package mage.cards.k;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;
import mage.filter.FilterOpponent;
import mage.target.TargetPlayer;

/**
 *
 * @author stevemarkham81
 */
public class KeeperOfTheLight extends CardImpl {

    public KeeperOfTheLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {W}, {tap}: Choose target opponent who had more life than you did as you activated this ability. You gain 3 life.
	Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, 
                    new GainLifeEffect(new StaticValue(3), "Choose target opponent who had more life than you did as you activated this ability. You gain 3 life."), 
                    new TapSourceCost(), KeeperOfTheLightCondition.instance);
        ability.addCost(new ManaCostsImpl("{W}"));
        ability.addTarget(new KeeperOfTheLightTarget());
        this.addAbility(ability);
                
    }

    public KeeperOfTheLight(final KeeperOfTheLight card) {
        super(card);
    }

    @Override
    public KeeperOfTheLight copy() {
        return new KeeperOfTheLight(this);
    }
}

class KeeperOfTheLightTarget extends TargetPlayer {

    public KeeperOfTheLightTarget() {
        this(false);
    }

    public KeeperOfTheLightTarget(boolean notTarget) {
        this(new FilterOpponent("opponent who had more life than you did as you activated this ability"), notTarget);
    }

    public KeeperOfTheLightTarget(FilterOpponent filter, boolean notTarget) {
        super(1, 1, notTarget, filter);
    }

    public KeeperOfTheLightTarget(final KeeperOfTheLightTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        int lifeController = game.getPlayer(sourceControllerId).getLife();
        
        for (UUID targetId : availablePossibleTargets) {
            Player opponent = game.getPlayer(targetId);
            if (opponent != null){
                int lifeOpponent = opponent.getLife();
                if (lifeOpponent > lifeController){
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }
    
    @Override
    public KeeperOfTheLightTarget copy() {
        return new KeeperOfTheLightTarget(this);
    }
    
    @Override
    public String toString() {
        return "opponent who had more life than you did as you activated this ability";
    }
}

enum KeeperOfTheLightCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int controllerLife = controller.getLife();
            
            for (UUID playerId : game.getPlayerList()){
                if (controllerLife < game.getPlayer(playerId).getLife()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you have less life than an opponent";
    }

}

