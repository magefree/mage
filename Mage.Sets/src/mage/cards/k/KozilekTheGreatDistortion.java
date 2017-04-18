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
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class KozilekTheGreatDistortion extends CardImpl {

    public KozilekTheGreatDistortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{C}{C}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Eldrazi");
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // When you cast Kozilek, the Great Distortion, if you have fewer than seven cards in hand, draw cards equal to the difference.
        this.addAbility(new ConditionalTriggeredAbility(
                new CastSourceTriggeredAbility(new KozilekDrawEffect(), false),
                new CardsInHandCondition(ComparisonType.FEWER_THAN, 7),
                "When you cast {this}, if you have fewer than seven cards in hand, draw cards equal to the difference."));
        // Menace
        this.addAbility(new MenaceAbility());

        // Discard a card with converted mana cost X: Counter target spell with converted mana cost X.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new KozilekDiscardCost());
        ability.addTarget(new TargetSpell(new FilterSpell("spell with converted mana cost X")));
        this.addAbility(ability);
    }

    public KozilekTheGreatDistortion(final KozilekTheGreatDistortion card) {
        super(card);
    }

    @Override
    public KozilekTheGreatDistortion copy() {
        return new KozilekTheGreatDistortion(this);
    }
}

class KozilekDrawEffect extends OneShotEffect {

    public KozilekDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "if you have fewer than seven cards in hand, draw cards equal to the difference";
    }

    public KozilekDrawEffect(final KozilekDrawEffect effect) {
        super(effect);
    }

    @Override
    public KozilekDrawEffect copy() {
        return new KozilekDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(7 - controller.getHand().size(), game);
            return true;
        }
        return false;
    }
}

class KozilekDiscardCost extends CostImpl {

    public KozilekDiscardCost() {
        this.text = "discard a card with converted mana cost X";
    }

    public KozilekDiscardCost(final KozilekDiscardCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Spell targetSpell = game.getStack().getSpell(ability.getFirstTarget());
        if (targetSpell == null) {
            return false;
        }
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card with converted mana cost of " + targetSpell.getConvertedManaCost());
        filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, targetSpell.getConvertedManaCost()));
        TargetCardInHand target = new TargetCardInHand(filter);
        this.getTargets().clear();
        this.getTargets().add(target);
        if (targets.choose(Outcome.Discard, controllerId, sourceId, game)) {
            for (UUID targetId : targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card == null) {
                    return false;
                }
                player.discard(card, ability, game);
                paid = true;
            }
        }
        return paid;
    }

    @Override
    public void clearPaid() {
        super.clearPaid();
        this.targets.clearChosen();
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        if (game.getStack().isEmpty()) {
            return false;
        }
        Set<Integer> stackCMC = new HashSet<>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell) {
                stackCMC.add(stackObject.getConvertedManaCost());
            }
        }
        Player controller = game.getPlayer(ability.getControllerId());
        for (Card card : controller.getHand().getCards(game)) {
            if (stackCMC.contains(card.getConvertedManaCost())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public KozilekDiscardCost copy() {
        return new KozilekDiscardCost(this);
    }

}
