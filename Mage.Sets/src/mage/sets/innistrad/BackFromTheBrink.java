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
package mage.sets.innistrad;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class BackFromTheBrink extends CardImpl {

    public BackFromTheBrink(UUID ownerId) {
        super(ownerId, 44, "Back from the Brink", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");
        this.expansionSetCode = "ISD";

        // Exile a creature card from your graveyard and pay its mana cost: Put a token onto the battlefield that's a copy of that card. Activate this ability only any time you could cast a sorcery.
        Effect effect = new PutTokenOntoBattlefieldCopyTargetEffect();
        effect.setText("Put a token onto the battlefield that's a copy of that card");
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, effect, new BackFromTheBrinkCost()));

    }

    public BackFromTheBrink(final BackFromTheBrink card) {
        super(card);
    }

    @Override
    public BackFromTheBrink copy() {
        return new BackFromTheBrink(this);
    }
}

class BackFromTheBrinkCost extends CostImpl {

    public BackFromTheBrinkCost() {
        Target target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard"));
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = "Exile a creature card from your graveyard and pay its mana cost";
    }

    public BackFromTheBrinkCost(final BackFromTheBrinkCost cost) {
        super(cost);
    }

    @Override
    public BackFromTheBrinkCost copy() {
        return new BackFromTheBrinkCost(this);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (targets.choose(Outcome.Exile, controllerId, sourceId, game)) {
            Player controller = game.getPlayer(controllerId);
            if (controller != null) {
                Card card = controller.getGraveyard().get(targets.getFirstTarget(), game);
                if (card != null && controller.moveCards(card, null, Zone.EXILED, ability, game)) {
                    ability.getEffects().get(0).setTargetPointer(new FixedTarget(card.getId()));
                    paid = card.getManaCost().pay(ability, game, sourceId, controllerId, noMana);
                }
            }
        }
        return paid;
    }

}
