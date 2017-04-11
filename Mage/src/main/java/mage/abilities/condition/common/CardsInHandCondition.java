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
package mage.abilities.condition.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.Condition;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * Cards in controller hand condition. This condition can decorate other
 * conditions as well as be used standalone.
 *
 * @author LevelX
 */
public class CardsInHandCondition implements Condition {


    private Condition condition;
    private ComparisonType type;
    private int count;
    private TargetController targetController;

    public CardsInHandCondition() {
        this(ComparisonType.EQUAL_TO, 0);
    }

    public CardsInHandCondition(ComparisonType type, int count) {
        this(type, count, null);
    }

    public CardsInHandCondition(ComparisonType type, int count, Condition conditionToDecorate) {
        this(type, count, conditionToDecorate, TargetController.YOU);
    }

    public CardsInHandCondition(ComparisonType type, int count, Condition conditionToDecorate, TargetController targetController) {
        this.type = type;
        this.count = count;
        this.condition = conditionToDecorate;
        this.targetController = targetController;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            switch (targetController) {
                case YOU:
                    conditionApplies = ComparisonType.compare(game.getPlayer(source.getControllerId()).getHand().size(), type, count);
                    break;
                case ACTIVE:
                    Player player = game.getPlayer(game.getActivePlayerId());
                    if (player != null) {
                        conditionApplies = ComparisonType.compare(player.getHand().size(), type, count);
                    }
                    break;
                case ANY:
                    boolean conflict = false;
                    for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                        player = game.getPlayer(playerId);
                        if (player != null) {
                            if (!ComparisonType.compare(player.getHand().size(), type, this.count)) {
                                conflict = true;
                                break;
                            }
                        }
                    }
                    conditionApplies = !conflict;
                    break;
                default:
                    throw new UnsupportedOperationException("Value of TargetController not supported for this class.");
            }

            //If a decorated condition exists, check it as well and apply them together.
            if (this.condition != null) {
                conditionApplies = conditionApplies && this.condition.apply(game, source);
            }
        }

        return conditionApplies;
    }

    @Override
    public String toString() {
        int workCount = count;
        StringBuilder sb = new StringBuilder("if ");
        switch (targetController) {
            case YOU:
                sb.append(" you have");
                break;
            case ANY:
                sb.append(" each player has");
                break;
        }
        switch (this.type) {
            case FEWER_THAN:
                sb.append(" less or equal than ");
                workCount++;
                break;
            case MORE_THAN:
                sb.append(" more than ");
                break;
            case EQUAL_TO:
                sb.append(" exactly ");
                break;
        }
        if (count == 0) {
            sb.append("no");
        } else {
            sb.append(CardUtil.numberToText(workCount));
        }
        sb.append(" cards in hand");
        return sb.toString();
    }

}
