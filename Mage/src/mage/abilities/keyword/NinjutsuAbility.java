/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.keyword;

import java.util.UUID;
import mage.Constants;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 * 702.47. Ninjutsu
 *
 * 702.47a Ninjutsu is an activated ability that functions only while the card 
 * with ninjutsu is in a player's hand. "Ninjutsu [cost]" means "[Cost], Reveal 
 * this card from your hand, Return an unblocked attacking creature you control 
 * to its owner's hand: Put this card onto the battlefield from your hand tapped
 * and attacking."
 *
 * 702.47b The card with ninjutsu remains revealed from the time the ability is
 * announced until the ability leaves the stack.
 *
 * 702.47c A ninjutsu ability may be activated only while a creature on the
 * battlefield is unblocked (see rule 509.1h). The creature with ninjutsu is
 * put onto the battlefield unblocked. It will be attacking the same player or
 * planeswalker as the creature that was returned to its owner's hand.
 *
 * @param cost ninjutsu mana cost
 *
 * @author LevelX2
 */
public class NinjutsuAbility extends ActivatedAbilityImpl<NinjutsuAbility> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("unblocked attacker you control");

    static {
        filter.add(new UnblockedPredicate());
    }

    public NinjutsuAbility(ManaCost manaCost) {
        super(Zone.HAND,new NinjutsuEffect(), manaCost);
        this.addCost(new RevealNinjutsuCardCost());
        this.addCost(new ReturnAttackerToHandTargetCost(new TargetControlledCreaturePermanent(1,1,filter,false)));
    }

    public NinjutsuAbility(NinjutsuAbility ability) {
        super(ability);
    }

    @Override
    public NinjutsuAbility copy() {
        return new NinjutsuAbility(this);
    }

    @Override
    public String getRule() {
        return "Ninjutsu " + getManaCostsToPay().getText()+ " <i>(" + getManaCostsToPay().getText() +" Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)</i>";
    }
}

class NinjutsuEffect extends OneShotEffect<NinjutsuEffect> {

    public NinjutsuEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        this.staticText = "Put this card onto the battlefield from your hand tapped and attacking";
    }

    public NinjutsuEffect(final NinjutsuEffect effect) {
        super(effect);
    }

    @Override
    public NinjutsuEffect copy() {
        return new NinjutsuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            card.putOntoBattlefield(game, Constants.Zone.HAND, source.getId(), source.getControllerId());
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                UUID defendingPlayerId = null;
                for (Cost cost :source.getCosts()) {
                    if (cost instanceof ReturnAttackerToHandTargetCost) {
                        defendingPlayerId = ((ReturnAttackerToHandTargetCost) cost).getDefendingPlayerId();
                    }
                }
                if (defendingPlayerId != null) {
                    game.getCombat().declareAttacker(permanent.getId(), defendingPlayerId, game);
                    permanent.setTapped(true);
                    return true;
                }
            }
        }
        return false;
    }
}

class ReturnAttackerToHandTargetCost extends CostImpl<ReturnAttackerToHandTargetCost> {

    private UUID defendingPlayerId;

    public ReturnAttackerToHandTargetCost(TargetControlledPermanent target) {
        this.addTarget(target);
        this.defendingPlayerId = null;
        this.text = "Return an unblocked attacker you control to hand";
    }

    public ReturnAttackerToHandTargetCost(ReturnAttackerToHandTargetCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (targets.choose(Constants.Outcome.ReturnToHand, controllerId, sourceId, game)) {
            for (UUID targetId: targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                defendingPlayerId = game.getCombat().getDefendingPlayer(permanent.getId());
                paid |= permanent.moveToZone(Zone.HAND, sourceId, game, false);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public ReturnAttackerToHandTargetCost copy() {
        return new ReturnAttackerToHandTargetCost(this);
    }

    public UUID getDefendingPlayerId() {
        return defendingPlayerId;
    }
}

class RevealNinjutsuCardCost extends CostImpl<RevealNinjutsuCardCost> {

    public RevealNinjutsuCardCost() {
        this.text = "reveal ninjutsu card";
    }

    public RevealNinjutsuCardCost(RevealNinjutsuCardCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player player = game.getPlayer(controllerId);

        Card card = player.getHand().get(ability.getSourceId(), game);
        if (card != null) {
            Cards cards = new CardsImpl(card);
            player.revealCards("Ninjutsu", cards, game);
            paid = true;
            return paid;
        }
        return false;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public RevealNinjutsuCardCost copy() {
        return new RevealNinjutsuCardCost(this);
    }
}
