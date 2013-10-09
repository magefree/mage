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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class EndlessHorizons extends CardImpl<EndlessHorizons> {

    public EndlessHorizons(UUID ownerId) {
        super(ownerId, 4, "Endless Horizons", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "EVE";

        this.color.setWhite(true);

        // When Endless Horizons enters the battlefield, search your library for any number of Plains cards and exile them. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EndlessHorizonsEffect(), false));

        // At the beginning of your upkeep, you may put a card you own exiled with Endless Horizons into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new EndlessHorizonsEffect2(), TargetController.YOU, true));

    }

    public EndlessHorizons(final EndlessHorizons card) {
        super(card);
    }

    @Override
    public EndlessHorizons copy() {
        return new EndlessHorizons(this);
    }
}

class EndlessHorizonsEffect extends SearchEffect<EndlessHorizonsEffect> {

    private static final FilterLandCard filter = new FilterLandCard("Plains card");

    static {
        filter.add(new SubtypePredicate("Plains"));
    }

    public EndlessHorizonsEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), Outcome.Neutral);
        this.staticText = "search your library for any number of Plains cards and exile them. Then shuffle your library";
    }

    public EndlessHorizonsEffect(final EndlessHorizonsEffect effect) {
        super(effect);
    }

    @Override
    public EndlessHorizonsEffect copy() {
        return new EndlessHorizonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null && you.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                for (UUID cardId : target.getTargets()) {
                    Card card = you.getLibrary().remove(cardId, game);
                    if (card != null) {
                        card.moveToExile(CardUtil.getCardExileZoneId(game, source), "Endless Horizons", source.getSourceId(), game);
                    }
                }
                you.shuffleLibrary(game);
                return true;
            }
        }
        return false;
    }
}

    class EndlessHorizonsEffect2 extends OneShotEffect<EndlessHorizonsEffect2> {

        public EndlessHorizonsEffect2() {
            super(Outcome.ReturnToHand);
            this.staticText = "you may put a card you own exiled with {this} into your hand";
        }

        public EndlessHorizonsEffect2(final EndlessHorizonsEffect2 effect) {
            super(effect);
        }

        @Override
        public EndlessHorizonsEffect2 copy() {
            return new EndlessHorizonsEffect2(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            ExileZone exZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            if (exZone != null) {
                for (Card card : exZone.getCards(game)) {
                    if (card != null
                            && card.getOwnerId() == source.getControllerId()) {
                        if (card.moveToZone(Zone.HAND, source.getId(), game, false)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    
}