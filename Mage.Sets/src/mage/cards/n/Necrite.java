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
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author MarcoMarin
 */
public class Necrite extends CardImpl {

    public Necrite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add("Thrull");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Necrite attacks and isn't blocked, you may sacrifice it. If you do, destroy target creature defending player controls. It can't be regenerated.
        DoIfCostPaid effect = new DoIfCostPaid(new DestroyTargetEffect(true), new SacrificeSourceCost(), "Sacrifice {this} to destroy target creature defending player controls?");
        effect.setText("you may sacrifice it. If you do, destroy target creature defending player controls. It can't be regenerated.");
        Ability ability = new NecriteTriggeredAbility(effect);
        this.addAbility(ability);
        
    }

    public Necrite(final Necrite card) {
        super(card);
    }

    @Override
    public Necrite copy() {
        return new Necrite(this);
    }
}

class NecriteTriggeredAbility extends AttacksAndIsNotBlockedTriggeredAbility{
    
    public NecriteTriggeredAbility(Effect effect) {
        super(effect);
    }

    public NecriteTriggeredAbility(final NecriteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NecriteTriggeredAbility copy() {
        return new NecriteTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)){
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
            FilterPermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(defendingPlayerId));
            Target target = new TargetPermanent(filter);
            this.getTargets().clear();
            this.addTarget(target);
            return true;
        }
        return false;
    }
}