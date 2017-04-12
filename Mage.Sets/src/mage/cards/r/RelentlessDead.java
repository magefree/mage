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
package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class RelentlessDead extends CardImpl {

    public RelentlessDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add("Zombie");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Relentless Dead dies, you may pay {B}. If you do, return it to its owner's hand.
        this.addAbility(new DiesTriggeredAbility(new DoIfCostPaid(new ReturnToHandSourceEffect(), new ManaCostsImpl("{B}"))));

        // When Relentless Dead dies, you may pay {X}. If you do, return another target Zombie creature card with converted mana cost X from your graveyard to the battlefield.
        this.addAbility(new DiesTriggeredAbility(new RelentlessDeadEffect()));
    }

    public RelentlessDead(final RelentlessDead card) {
        super(card);
    }

    @Override
    public RelentlessDead copy() {
        return new RelentlessDead(this);
    }
}

class RelentlessDeadEffect extends OneShotEffect {

    public RelentlessDeadEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may pay {X}. If you do, return another target Zombie creature card with converted mana cost X from your graveyard to the battlefield";
    }

    public RelentlessDeadEffect(final RelentlessDeadEffect effect) {
        super(effect);
    }

    @Override
    public RelentlessDeadEffect copy() {
        return new RelentlessDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.BoostCreature, "Do you want to pay {X}?", source, game)) {
                int costX = controller.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
                Cost cost = new GenericManaCost(costX);
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    FilterCard filter = new FilterCard("Another target Zombie card with converted mana cost " + costX);
                    filter.add(new SubtypePredicate("Zombie"));
                    filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, costX));
                    filter.add(new AnotherCardPredicate());
                    TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }
}
