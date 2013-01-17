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
package mage.sets.odyssey;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class SkeletalScrying extends CardImpl<SkeletalScrying> {

    public SkeletalScrying(UUID ownerId) {
        super(ownerId, 161, "Skeletal Scrying", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{X}{B}");
        this.expansionSetCode = "ODY";

        this.color.setBlack(true);

        // As an additional cost to cast Skeletal Scrying, exile X cards from your graveyard.
        this.getSpellAbility().addCost(new ExileXFromGraveyardCost());
        
        // You draw X cards and you lose X life.
        this.getSpellAbility().addEffect(new SkeletalScryingEffect(new GetXValue()));
        
    }

    public SkeletalScrying(final SkeletalScrying card) {
        super(card);
    }

    @Override
    public SkeletalScrying copy() {
        return new SkeletalScrying(this);
    }
}

class ExileXFromGraveyardCost extends CostImpl<ExileXFromGraveyardCost> implements VariableCost  {

    protected int amountPaid = 0;

    public ExileXFromGraveyardCost() {
        this.text = "As an additional cost to cast Skeletal Scrying, exile X cards from your graveyard";
    }

    public ExileXFromGraveyardCost(final ExileXFromGraveyardCost cost) {
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
        Target target = new TargetCardInYourGraveyard();
        Player player = game.getPlayer(controllerId);
        while (true) {
            target.clearChosen();
            if (target.canChoose(controllerId, game) && target.choose(Constants.Outcome.Exile, controllerId, sourceId, game)) {
                Card card = player.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    player.getGraveyard().remove(card);
                    card.moveToExile(null, "", sourceId, game);
                    amountPaid++;
                }
            }
            else 
                break;
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
    public ExileXFromGraveyardCost copy() {
        return new ExileXFromGraveyardCost(this);
    }

    @Override
    public void setAmount(int amount) {
        amountPaid = amount;
    }

}

class SkeletalScryingEffect extends OneShotEffect<SkeletalScryingEffect> {

    protected DynamicValue amount;

    public SkeletalScryingEffect(int amount) {
        this(new StaticValue(amount));
    }
    
    public SkeletalScryingEffect(DynamicValue amount) {
        super(Constants.Outcome.Neutral);
        this.amount = amount.copy();
        staticText = "You draw " + amount + " cards and you lose " + amount + " life";
    }

    public SkeletalScryingEffect(final SkeletalScryingEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SkeletalScryingEffect copy() {
        return new SkeletalScryingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
            if ( controller != null ) {
                controller.drawCards(amount.calculate(game, source), game);
                controller.loseLife(amount.calculate(game, source), game);
                return true;
            }
        return false;
    }
}
