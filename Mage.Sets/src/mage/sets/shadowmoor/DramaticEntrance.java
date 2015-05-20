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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class DramaticEntrance extends CardImpl {

    public DramaticEntrance(UUID ownerId) {
        super(ownerId, 111, "Dramatic Entrance", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");
        this.expansionSetCode = "SHM";


        // You may put a green creature card from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new DramaticEntranceEffect());

    }

    public DramaticEntrance(final DramaticEntrance card) {
        super(card);
    }

    @Override
    public DramaticEntrance copy() {
        return new DramaticEntrance(this);
    }
}

class DramaticEntranceEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a green creature card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public DramaticEntranceEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a green creature card from your hand onto the battlefield";
    }

    public DramaticEntranceEffect(final DramaticEntranceEffect effect) {
        super(effect);
    }

    @Override
    public DramaticEntranceEffect copy() {
        return new DramaticEntranceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getHand().count(filter, game) > 0) {
                if (controller.chooseUse(Outcome.PutCreatureInPlay,
                        "Put a green creature card onto the battlefield?", game)) {
                    Target target = new TargetCardInHand(filter);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            controller.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId());
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
