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
package mage.sets.shardsofalara;

import java.util.UUID;

import mage.constants.*;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.sets.tokens.EmptyToken;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public class MinionReflector extends CardImpl<MinionReflector> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");
    static{
        filter.add(Predicates.not(new TokenPredicate()));
    }
    
    public MinionReflector(UUID ownerId) {
        super(ownerId, 211, "Minion Reflector", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "ALA";

        // Whenever a nontoken creature enters the battlefield under your control, you may pay {2}. If you do, put a token that's a copy of that creature onto the battlefield. That token has haste and "At the beginning of the end step, sacrifice this permanent."
        Ability ability = new MinionReflectorTriggeredAbility();
        ability.addCost(new ManaCostsImpl("{2}"));
        this.addAbility(ability);
    }

    public MinionReflector(final MinionReflector card) {
        super(card);
    }

    @Override
    public MinionReflector copy() {
        return new MinionReflector(this);
    }
}

class MinionReflectorTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {


    public MinionReflectorTriggeredAbility() {
        super(new MinionReflectorEffect(), new FilterControlledCreaturePermanent(), "Whenever a nontoken creature enters the battlefield under your control, you may pay {2}. If you do, put a token that's a copy of that creature onto the battlefield. That token has haste and \"At the beginning of the end step, sacrifice this permanent");
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public MinionReflectorTriggeredAbility(MinionReflectorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            UUID targetId = event.getTargetId();
            Permanent permanent = game.getPermanent(targetId);
            if (filter.match(permanent, getSourceId(), getControllerId(), game)) {
                for(Effect effect : this.getEffects()){
                    effect.setTargetPointer(new FixedTarget(targetId));
                }
                return true;
            }
        }
        return false;
    }



    @Override
    public MinionReflectorTriggeredAbility copy() {
        return new MinionReflectorTriggeredAbility(this);
    }
    
    
}


class MinionReflectorEffect extends OneShotEffect<MinionReflectorEffect> {

    public MinionReflectorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a token that's a copy of that creature onto the battlefield. That token has haste and \"At the beginning of the end step, sacrifice this permanent.";
    }

    public MinionReflectorEffect(final MinionReflectorEffect effect) {
        super(effect);
    }

    @Override
    public MinionReflectorEffect copy() {
        return new MinionReflectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        if (permanent != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(permanent);

            token.addAbility(HasteAbility.getInstance());
            token.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.ANY, true));
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }

        return false;
    }
}
