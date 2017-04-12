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

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class RallyTheAncestors extends CardImpl {

    public RallyTheAncestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{W}{W}");

        // Return each creature card with converted mana cost X or less from your graveyard to the battlefield.
        // Exile those creatures at the beginning of your next upkeep. Exile Rally the Ancestors.
        this.getSpellAbility().addEffect(new RallyTheAncestorsEffect());
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public RallyTheAncestors(final RallyTheAncestors card) {
        super(card);
    }

    @Override
    public RallyTheAncestors copy() {
        return new RallyTheAncestors(this);
    }
}

class RallyTheAncestorsEffect extends OneShotEffect {

    RallyTheAncestorsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return each creature card with converted mana cost X or less from your graveyard to the battlefield. Exile those creatures at the beginning of your next upkeep";
    }

    RallyTheAncestorsEffect(final RallyTheAncestorsEffect effect) {
        super(effect);
    }

    @Override
    public RallyTheAncestorsEffect copy() {
        return new RallyTheAncestorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int xValue = source.getManaCostsToPay().getX();
            FilterCreatureCard filter = new FilterCreatureCard();
            filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue + 1));
            Set<Card> cards = player.getGraveyard().getCards(filter, game);
            player.moveCards(cards, Zone.BATTLEFIELD, source, game);
            ArrayList<Permanent> toExile = new ArrayList<>(cards.size());
            for (Card card : cards) {
                if (card != null) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        toExile.add(permanent);
                    }
                }
            }
            Effect exileEffect = new ExileTargetEffect("Exile those creatures at the beginning of your next upkeep");
            exileEffect.setTargetPointer(new FixedTargets(toExile, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(exileEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
