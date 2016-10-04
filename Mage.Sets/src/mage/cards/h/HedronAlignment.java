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
package mage.cards.h;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class HedronAlignment extends CardImpl {

    public HedronAlignment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // At the beginning of your upkeep, you may reveal your hand. If you do, you win the game if you own a card named Hedron Alignment in exile, in your hand, in your graveyard, and on the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new HedronAlignmentEffect(), TargetController.YOU, true));
        // {1}{U}: Scry 1.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new ManaCostsImpl("{1}{U}")));
    }

    public HedronAlignment(final HedronAlignment card) {
        super(card);
    }

    @Override
    public HedronAlignment copy() {
        return new HedronAlignment(this);
    }
}

class HedronAlignmentEffect extends OneShotEffect {

    private final static FilterPermanent filterPermanent = new FilterPermanent();
    private final static FilterCard filterCard = new FilterCard();

    static {
        filterPermanent.add(new NamePredicate("Hedron Alignment"));
        filterPermanent.add(new OwnerPredicate(TargetController.YOU));
        filterCard.add(new NamePredicate("Hedron Alignment"));
        filterCard.add(new OwnerPredicate(TargetController.YOU));
    }

    public HedronAlignmentEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may reveal your hand. If you do, you win the game if you own a card named Hedron Alignment in exile, in your hand, in your graveyard, and on the battlefield";
    }

    public HedronAlignmentEffect(final HedronAlignmentEffect effect) {
        super(effect);
    }

    @Override
    public HedronAlignmentEffect copy() {
        return new HedronAlignmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards cardsToReveal = new CardsImpl();
            controller.revealCards(sourceObject.getIdName(), cardsToReveal, game);
            // Check battlefield
            if (!game.getBattlefield().contains(filterPermanent, source.getControllerId(), game, 1)) {
                return true;
            }
            if (controller.getHand().getCards(filterCard, source.getSourceId(), controller.getId(), game).isEmpty()) {
                return true;
            }
            if (controller.getGraveyard().getCards(filterCard, source.getSourceId(), controller.getId(), game).isEmpty()) {
                return true;
            }
            Cards cardsToCheck = new CardsImpl();
            cardsToCheck.addAll(game.getExile().getAllCards(game));
            if (cardsToCheck.count(filterCard, source.getSourceId(), controller.getId(), game) == 0) {
                return true;
            }
            controller.won(game);
            return true;

        }
        return false;
    }
}
