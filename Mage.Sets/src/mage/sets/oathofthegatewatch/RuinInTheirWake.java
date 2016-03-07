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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class RuinInTheirWake extends CardImpl {

    public RuinInTheirWake(UUID ownerId) {
        super(ownerId, 122, "Ruin in Their Wake", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.expansionSetCode = "OGW";

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Search your library for a basic land card and reveal it. You may put that card onto the battlefield tapped if you control a land named Wastes. Otherwise, put that card into your hand. Then shuffle your library.
        getSpellAbility().addEffect(new RuinInTheirWakeEffect());
    }

    public RuinInTheirWake(final RuinInTheirWake card) {
        super(card);
    }

    @Override
    public RuinInTheirWake copy() {
        return new RuinInTheirWake(this);
    }
}

class RuinInTheirWakeEffect extends OneShotEffect {

    private final static FilterLandPermanent filterWastes = new FilterLandPermanent();

    static {
        filterWastes.add(new NamePredicate("Wastes"));
    }

    public RuinInTheirWakeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a basic land card and reveal it. You may put that card onto the battlefield tapped if you control a land named Wastes. Otherwise, put that card into your hand. Then shuffle your library";
    }

    public RuinInTheirWakeEffect(final RuinInTheirWakeEffect effect) {
        super(effect);
    }

    @Override
    public RuinInTheirWakeEffect copy() {
        return new RuinInTheirWakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(new FilterBasicLandCard());
            if (controller.searchLibrary(target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    Cards cardsToReveal = new CardsImpl(card);
                    controller.revealCards(sourceObject.getIdName(), cardsToReveal, game);
                    boolean controlWastes = game.getBattlefield().countAll(filterWastes, controller.getId(), game) > 0;
                    if (controlWastes && controller.chooseUse(outcome, "Put " + card.getLogName() + " onto battlefield tapped?", source, game)) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
                    } else {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
