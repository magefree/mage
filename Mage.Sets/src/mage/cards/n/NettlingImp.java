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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.TargetAttackedThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetAtBeginningOfNextEndStepEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControlledFromStartOfControllerTurnPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author MTGfan
 */
public class NettlingImp extends CardImpl {
    
    final static FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall");

    static {
        filter.add(Predicates.not(new SubtypePredicate("Wall")));
	filter.add(new ControlledFromStartOfControllerTurnPredicate());
        filter.add(new ControllerPredicate(TargetController.ACTIVE));
        filter.setMessage("non-Wall creature the active player has controlled continuously since the beginning of the turn.");
    }


    public NettlingImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add("Imp");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. Activate this ability only during an opponent's turn, before attackers are declared.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleTargetEffect(Duration.EndOfTurn), new TapSourceCost(), new NettlingImpTurnCondition(), "{T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. Activate this ability only during an opponent's turn, before attackers are declared.");
        ability.addEffect(new ConditionalOneShotEffect(new DestroyTargetAtBeginningOfNextEndStepEffect(), new InvertCondition(new TargetAttackedThisTurnCondition())));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability, new AttackedThisTurnWatcher());
        
    }

    public NettlingImp(final NettlingImp card) {
        super(card);
    }

    @Override
    public NettlingImp copy() {
        return new NettlingImp(this);
    }
}

class NettlingImpTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
	Player activePlayer = game.getPlayer(game.getActivePlayerId());
        return activePlayer != null && activePlayer.hasOpponent(source.getControllerId(), game) && game.getPhase().getStep().getType().getIndex() < 5;
    }

    @Override
    public String toString() {
        return "";
    }
}
