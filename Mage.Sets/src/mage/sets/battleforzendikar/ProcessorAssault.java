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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ProcessorAssault extends CardImpl {

    public ProcessorAssault(UUID ownerId) {
        super(ownerId, 132, "Processor Assault", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{R}");
        this.expansionSetCode = "BFZ";

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // As an additional cost to cast Processor Assault, put a card an opponent owns from exile into its owner's graveyard.
        this.getSpellAbility().addCost(new PutOpponentExileCardToGraveyardCost());

        // Processor Assault deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public ProcessorAssault(final ProcessorAssault card) {
        super(card);
    }

    @Override
    public ProcessorAssault copy() {
        return new ProcessorAssault(this);
    }
}

class PutOpponentExileCardToGraveyardCost extends CostImpl {

    public PutOpponentExileCardToGraveyardCost() {
        this.text = "put a card an opponent owns from exile into its owner's graveyard";
    }

    public PutOpponentExileCardToGraveyardCost(PutOpponentExileCardToGraveyardCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            FilterCard filter = new FilterCard();
            filter.add(new OwnerPredicate(TargetController.OPPONENT));
            Target target = new TargetCardInExile(filter);
            if (controller.chooseTarget(Outcome.Damage, target, ability, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    paid = true;
                    controller.moveCards(card, null, Zone.GRAVEYARD, ability, game);
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            for (Card card : game.getExile().getAllCards(game)) {
                if (controller.hasOpponent(card.getOwnerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PutOpponentExileCardToGraveyardCost copy() {
        return new PutOpponentExileCardToGraveyardCost(this);
    }

}
