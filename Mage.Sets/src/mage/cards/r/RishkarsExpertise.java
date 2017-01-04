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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public class RishkarsExpertise extends CardImpl {

    public RishkarsExpertise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");


        // Draw cards equal to the greatest power among creatures you control.
        getSpellAbility().addEffect(new RishkarsExpertiseDrawEffect());

        // You may cast a card with converted mana cost 5 or less from your hand without paying its mana cost.
        getSpellAbility().addEffect(new RishkarsExpertiseCastEffect());
    }

    public RishkarsExpertise(final RishkarsExpertise card) {
        super(card);
    }

    @Override
    public RishkarsExpertise copy() {
        return new RishkarsExpertise(this);
    }
}

class RishkarsExpertiseDrawEffect extends OneShotEffect {

    RishkarsExpertiseDrawEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw cards equal to the greatest power among creatures you control";
    }

    RishkarsExpertiseDrawEffect(final RishkarsExpertiseDrawEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = 0;
            for (Permanent p : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), game)) {
                if (p.getPower().getValue() > amount) {
                    amount = p.getPower().getValue();
                }
            }
            player.drawCards(amount, game);
            return true;
        }
        return false;
    }

    @Override
    public RishkarsExpertiseDrawEffect copy() {
        return new RishkarsExpertiseDrawEffect(this);
    }

}

class RishkarsExpertiseCastEffect extends OneShotEffect {

    private static final FilterNonlandCard filter = new FilterNonlandCard("card with converted mana cost 5 or less from your hand");

    static {
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, 6));
    }

    public RishkarsExpertiseCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast a card with converted mana cost 5 or less from your hand without paying its mana cost";
    }

    public RishkarsExpertiseCastEffect(final RishkarsExpertiseCastEffect effect) {
        super(effect);
    }

    @Override
    public RishkarsExpertiseCastEffect copy() {
        return new RishkarsExpertiseCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInHand(filter);
            if (target.canChoose(source.getSourceId(), controller.getId(), game) &&
              controller.chooseUse(outcome, "Cast a card with converted mana cost 5 or less from your hand without paying its mana cost?", source, game)) {
                Card cardToCast = null;
                boolean cancel = false;
                while (controller.canRespond() && !cancel) {
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        cardToCast = game.getCard(target.getFirstTarget());
                        if (cardToCast != null && cardToCast.getSpellAbility().canChooseTarget(game)) {
                            cancel = true;
                        }
                    } else {
                        cancel = true;
                    }
                }
                if (cardToCast != null) {
                    controller.cast(cardToCast.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
