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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsDamageToOneOrMoreCreaturesTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.PutPermanentOnBattlefieldEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class BriarbridgePatrol extends CardImpl {

    public BriarbridgePatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Briarbridge Patrol deals damage to one or more creatures, investigate (Create a colorless Clue artifact token with "2, Sacrifice this artifact: Draw a card.").
        this.addAbility(new DealsDamageToOneOrMoreCreaturesTriggeredAbility(new InvestigateEffect(), false, false, false));
        // At the beginning of each end step, if you sacrificed three or more Clues this turn, you may put a creature card from your hand onto the battlefield.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new PutPermanentOnBattlefieldEffect(new FilterCreatureCard("a creature card")), TargetController.ANY,
                BriarbridgePatrolCondition.instance, true), new PermanentsSacrificedWatcher());

    }

    public BriarbridgePatrol(final BriarbridgePatrol card) {
        super(card);
    }

    @Override
    public BriarbridgePatrol copy() {
        return new BriarbridgePatrol(this);
    }
}

enum BriarbridgePatrolCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsSacrificedWatcher watcher = (PermanentsSacrificedWatcher) game.getState().getWatchers().get(PermanentsSacrificedWatcher.class.getSimpleName());
        if (watcher != null) {
            List<Permanent> sacrificedPermanents = watcher.getThisTurnSacrificedPermanents(source.getControllerId());
            if (sacrificedPermanents != null && !sacrificedPermanents.isEmpty()) {
                int amountOfClues = 0;
                for (Permanent permanent : sacrificedPermanents) {
                    if (permanent.getSubtype(game).contains("Clue")) {
                        amountOfClues++;
                    }
                }
                return amountOfClues > 2;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if you sacrificed three or more Clues this turn";
    }

    }
