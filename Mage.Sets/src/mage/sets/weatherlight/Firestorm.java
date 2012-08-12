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
package mage.sets.weatherlight;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class Firestorm extends CardImpl<Firestorm> {

    public Firestorm(UUID ownerId) {
        super(ownerId, 101, "Firestorm", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "WTH";

        this.color.setRed(true);

        // As an additional cost to cast Firestorm, discard X cards.
        // Firestorm deals X damage to each of X target creatures and/or players.
        this.getSpellAbility().addEffect(new FirestormEffect());
        this.getSpellAbility().addCost(new FirestormCost());

    }

    public Firestorm(final Firestorm card) {
        super(card);
    }

    @Override
    public Firestorm copy() {
        return new Firestorm(this);
    }
}

class FirestormEffect extends OneShotEffect<FirestormEffect> {

    public FirestormEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "{this} deals X damage to each of X target creatures and/or players";
    }

    public FirestormEffect(final FirestormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        int amount = (new GetXValue()).calculate(game, source);
        TargetCreatureOrPlayer target = new TargetCreatureOrPlayer(amount);
        if (you != null) {
            if (target.canChoose(source.getControllerId(), game) && target.choose(Constants.Outcome.Neutral, source.getControllerId(), source.getId(), game)) {
                if (!target.getTargets().isEmpty()) {
                    List<UUID> targets = target.getTargets();
                    for (UUID targetId : targets) {
                        Permanent creature = game.getPermanent(targetId);
                        if (creature != null) {
                            creature.damage(amount, source.getSourceId(), game, true, false);
                        } else {
                            Player player = game.getPlayer(targetId);
                            if (player != null) {
                                player.damage(amount, source.getSourceId(), game, true, false);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;

    }

    @Override
    public FirestormEffect copy() {
        return new FirestormEffect(this);
    }
}

class FirestormCost extends CostImpl<FirestormCost> implements VariableCost {

    protected int amountPaid = 0;

    public FirestormCost() {
        this.text = "discard X cards.";
    }

    public FirestormCost(final FirestormCost cost) {
        super(cost);
        this.amountPaid = cost.amountPaid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        amountPaid = 0;
        Target target = new TargetCardInHand();
        Player you = game.getPlayer(controllerId);
        while (true) {
            target.clearChosen();
            if (target.canChoose(controllerId, game) && target.choose(Constants.Outcome.Discard, controllerId, sourceId, game)) {
                Card card = you.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    you.getHand().remove(card);
                    card.moveToZone(Constants.Zone.GRAVEYARD, sourceId, game, false);
                    amountPaid++;
                }
            } else {
                break;
            }
        }
        paid = true;
        return true;
    }

    @Override
    public int getAmount() {
        return amountPaid;
    }

    @Override
    public void setFilter(FilterMana filter) {
    }

    @Override
    public FirestormCost copy() {
        return new FirestormCost(this);
    }

    @Override
    public void setAmount(int amount) {
        amountPaid = amount;
    }
}
