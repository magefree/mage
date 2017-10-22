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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author L_J
 */
public class CustodiSoulcaller extends CardImpl {

    public CustodiSoulcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Melee
        this.addAbility(new MeleeAbility());

        // Whenever Custodi Soulcaller attacks, return target creature card with converted mana cost X or less from your graveyard to the battlefield, where X is the number of players you attacked with a creature this combat.
        this.addAbility(new AttacksTriggeredAbility(new CustodiSoulcallerEffect(), false));
    }

    public CustodiSoulcaller(final CustodiSoulcaller card) {
        super(card);
    }

    @Override
    public CustodiSoulcaller copy() {
        return new CustodiSoulcaller(this);
    }
}

class CustodiSoulcallerEffect extends OneShotEffect {

    public CustodiSoulcallerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return target creature card with converted mana cost X or less from your graveyard to the battlefield, where X is the number of players you attacked with a creature this combat";
    }

    public CustodiSoulcallerEffect(final CustodiSoulcallerEffect effect) {
        super(effect);
    }

    @Override
    public CustodiSoulcallerEffect copy() {
        return new CustodiSoulcallerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int costX = new MeleeAbility().calculate(game, source, null);
            FilterCard filter = new FilterCard("creature card with converted mana cost " + costX + " or less");
            filter.add(new CardTypePredicate(CardType.CREATURE));
            filter.add(Predicates.or(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, costX), new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, costX)));
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
            if (controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;

    }
}
