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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class PorphyryNodes extends CardImpl<PorphyryNodes> {
    
    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    
    public PorphyryNodes(UUID ownerId) {
        super(ownerId, 28, "Porphyry Nodes", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "PLC";

        this.color.setWhite(true);

        // At the beginning of your upkeep, destroy the creature with the least power. It can't be regenerated. If two or more creatures are tied for least power, you choose one of them.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PorphyryNodesEffect(), TargetController.YOU, false));

        // When there are no creatures on the battlefield, sacrifice Porphyry Nodes.
        this.addAbility(new PorphyryNodesStateTriggeredAbility());
        
    }

    public PorphyryNodes(final PorphyryNodes card) {
        super(card);
    }

    @Override
    public PorphyryNodes copy() {
        return new PorphyryNodes(this);
    }
}

class PorphyryNodesEffect extends OneShotEffect<PorphyryNodesEffect> {
    
    public PorphyryNodesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy the creature with the least power. It can't be regenerated. If two or more creatures are tied for least power, you choose one of them";
    }
    
    public PorphyryNodesEffect(final PorphyryNodesEffect effect) {
        super(effect);
    }
    
    @Override
    public PorphyryNodesEffect copy() {
        return new PorphyryNodesEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            int leastPower = Integer.MAX_VALUE;
            boolean multipleExist = false;
            Permanent permanentToDestroy = null;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(PorphyryNodes.filter, source.getControllerId(), game)) {
                if (permanent.getPower().getValue() < leastPower) {
                    permanentToDestroy = permanent;
                    leastPower = permanent.getPower().getValue();
                    multipleExist = false;
                } else {
                    if (permanent.getPower().getValue() == leastPower) {
                        multipleExist = true;
                    }
                }
            }
            if (multipleExist) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("one of the creatures with the least power");
                filter.add(new PowerPredicate(Filter.ComparisonType.Equal, leastPower));
                Target target = new TargetPermanent(filter, true);
                target.setNotTarget(true);
                if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                    if (controller.choose(outcome, target, source.getSourceId(), game)) {
                        permanentToDestroy = game.getPermanent(target.getFirstTarget());
                    }
                }
            }
            if (permanentToDestroy != null) {
                game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(" chosen creature: ").append(permanentToDestroy.getName()).toString());
                return permanentToDestroy.destroy(source.getSourceId(), game, true);
            }
            return true;
        }

        return false;
    }
}

class PorphyryNodesStateTriggeredAbility extends StateTriggeredAbility<PorphyryNodesStateTriggeredAbility> {

    public PorphyryNodesStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public PorphyryNodesStateTriggeredAbility(final PorphyryNodesStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PorphyryNodesStateTriggeredAbility copy() {
        return new PorphyryNodesStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().count(PorphyryNodes.filter, this.getSourceId(), this.getControllerId(), game) == 0;
    }

    @Override
    public String getRule() {
        return new StringBuilder("When there are no creatures on the battlefield, ").append(super.getRule()).toString() ;
    }

}
