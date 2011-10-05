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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ControllerWinsEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author Rafbill
 */
public class FelidarSovereign extends CardImpl<FelidarSovereign> {

    public FelidarSovereign(UUID ownerId) {
        super(ownerId, 12, "Felidar Sovereign", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Cat");
        this.subtype.add("Beast");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
        // At the beginning of your upkeep, if you have 40 or more life, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new ControllerWinsEffect(), Constants.TargetController.YOU, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, new FortyOrMoreLifeCondition(FortyOrMoreLifeCondition.CheckType.CONTROLLER), "At the beginning of your upkeep, if you have 40 or more life, you win the game."));

    }

    public FelidarSovereign(final FelidarSovereign card) {
        super(card);
    }

    @Override
    public FelidarSovereign copy() {
        return new FelidarSovereign(this);
    }
}

class FortyOrMoreLifeCondition implements Condition {

    public static enum CheckType {

        AN_OPPONENT, CONTROLLER, TARGET_OPPONENT
    };
    private CheckType type;

    public FortyOrMoreLifeCondition(CheckType type) {
        this.type = type;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;

        switch (this.type) {
            case AN_OPPONENT:
                for (UUID opponentUUID : game.getOpponents(source.getControllerId())) {
                    conditionApplies |= game.getPlayer(opponentUUID).getLife() >= 40;
                }
                break;
            case CONTROLLER:
                conditionApplies |= game.getPlayer(source.getControllerId()).getLife() >= 40;
                break;
            case TARGET_OPPONENT:
                //TODO: Implement this.
                break;
        }

        return conditionApplies;
    }
}