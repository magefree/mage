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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.sets.tokens.EmptyToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward
 */
public class BackFromTheBrink extends CardImpl<BackFromTheBrink> {

    public BackFromTheBrink(UUID ownerId) {
        super(ownerId, 44, "Back from the Brink", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");
        this.expansionSetCode = "ISD";

        this.color.setBlue(true);

        // Exile a creature card from your graveyard and pay its mana cost: Put a token onto the battlefield that's a copy of that card. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new BackFromTheBrinkEffect(), new BackFromTheBrinkCost()));
        
    }

    public BackFromTheBrink(final BackFromTheBrink card) {
        super(card);
    }

    @Override
    public BackFromTheBrink copy() {
        return new BackFromTheBrink(this);
    }
}

class BackFromTheBrinkEffect extends OneShotEffect<BackFromTheBrinkEffect> {

    public BackFromTheBrinkEffect () {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put a token onto the battlefield that's a copy of that card. Activate this ability only any time you could cast a sorcery";
    }
    
    public BackFromTheBrinkEffect(final BackFromTheBrinkEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.targetPointer.getFirst(source));
        if (card != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(card);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }

    @Override
    public BackFromTheBrinkEffect copy() {
        return new BackFromTheBrinkEffect(this);
    }
    
}

class BackFromTheBrinkCost extends CostImpl<BackFromTheBrinkCost> {

    public BackFromTheBrinkCost() {
        this.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
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
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (targets.choose(Outcome.Exile, controllerId, sourceId, game)) {
            Player player = game.getPlayer(controllerId);
            if (player != null) {
                Card card = player.getGraveyard().get(targets.getFirstTarget(), game);
                if (card != null && card.moveToZone(Constants.Zone.EXILED, sourceId, game, false)) {
                    ability.getEffects().get(0).setTargetPointer(new FixedTarget(card.getId()));
                    paid = card.getManaCost().pay(ability, game, sourceId, controllerId, noMana);
                }
            }
        }
        return paid;
    }
    
}