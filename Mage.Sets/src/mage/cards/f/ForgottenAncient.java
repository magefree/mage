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
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Blinke
 */
public class ForgottenAncient extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
    static {
        filter.add(new AnotherPredicate());
    }

    public ForgottenAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add("Elemental");
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Whenever a player casts a spell, you may put a +1/+1 counter on Forgotten Ancient.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance());
        Ability ability = new SpellCastAllTriggeredAbility(effect, true);
        this.addAbility(ability);
        
        // At the beginning of your upkeep, you may move any number of +1/+1 counters from Forgotten Ancient onto other creatures.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ForgottenAncientEffect(), TargetController.YOU, true));
    }

    public ForgottenAncient(final ForgottenAncient card) {
        super(card);
    }

    @Override
    public ForgottenAncient copy() {
        return new ForgottenAncient(this);
    }
    
    class CounterMovement {
        public UUID target;
        public int counters;
    }
    
    class ForgottenAncientEffect extends OneShotEffect {
        
        public ForgottenAncientEffect() {
            super(Outcome.Benefit);
            this.staticText = "you may move any number of +1/+1 counters from {this} onto other creatures.";
        }
        
        public ForgottenAncientEffect(final ForgottenAncientEffect effect) {
            super(effect);
        }
        
        @Override
        public ForgottenAncientEffect copy() {
            return new ForgottenAncientEffect(this);
        }
        
        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());

            if(controller == null || sourcePermanent == null) {
                return false;
            }
            
            int numCounters = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            ArrayList<CounterMovement> counterMovements = new ArrayList<>();
            
            do {
                Target target = new TargetCreaturePermanent(1, 1, filter, true);
                if(numCounters == 0 || !target.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), game)) {
                    continue;
                }

                int amountToMove = controller.getAmount(0, numCounters, "How many counters do you want to move? " + '(' + numCounters + ')' + " counters remaining.", game);
                if(amountToMove > 0)
                {
                    boolean previouslyChosen = false;
                    for (CounterMovement cm : counterMovements) {
                        if(cm.target.equals(target.getFirstTarget()))
                        {
                            cm.counters += amountToMove;
                            previouslyChosen = true;
                        }
                    }
                    if(!previouslyChosen) {
                        CounterMovement cm = new CounterMovement();
                        cm.target = target.getFirstTarget();
                        cm.counters = amountToMove;
                        counterMovements.add(cm);
                    }
                    
                    numCounters -= amountToMove;
                }
            } while(numCounters > 0 && controller.chooseUse(Outcome.Benefit, "Move additonal counters?", source, game));
            
            //Move all the counters for each chosen creature
            for(CounterMovement cm: counterMovements) {
                sourcePermanent.removeCounters(CounterType.P1P1.createInstance(cm.counters), game);
                game.getPermanent(cm.target).addCounters(CounterType.P1P1.createInstance(cm.counters), source, game);
            }
            return true;
        }
    }
}
