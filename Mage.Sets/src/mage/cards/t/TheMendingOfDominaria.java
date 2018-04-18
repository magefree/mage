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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public class TheMendingOfDominaria extends CardImpl {

    public TheMendingOfDominaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

        // I, II — Put the top two cards of your library into your graveyard, then you may return a creature card from your graveyard to your hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new TheMendingOfDominariaFirstEffect());

        // III — Return all land cards from your graveyard to the battlefield, then shuffle your graveyard into your library.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheMendingOfDominariaSecondEffect());
        this.addAbility(sagaAbility);
    }

    public TheMendingOfDominaria(final TheMendingOfDominaria card) {
        super(card);
    }

    @Override
    public TheMendingOfDominaria copy() {
        return new TheMendingOfDominaria(this);
    }
}

class TheMendingOfDominariaFirstEffect extends OneShotEffect {

    public TheMendingOfDominariaFirstEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Put the top two cards of your library into your graveyard, then you may return a creature card from your graveyard to your hand";
    }

    public TheMendingOfDominariaFirstEffect(final TheMendingOfDominariaFirstEffect effect) {
        super(effect);
    }

    @Override
    public TheMendingOfDominariaFirstEffect copy() {
        return new TheMendingOfDominariaFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        new PutTopCardOfLibraryIntoGraveControllerEffect(2).apply(game, source);
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                && controller.chooseUse(outcome, "Return a creature card from your graveyard to hand?", source, game)
                && controller.choose(Outcome.ReturnToHand, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }
}

class TheMendingOfDominariaSecondEffect extends OneShotEffect {

    TheMendingOfDominariaSecondEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return all land cards from your graveyard to the battlefield, then shuffle your graveyard into your library";
    }

    TheMendingOfDominariaSecondEffect(final TheMendingOfDominariaSecondEffect effect) {
        super(effect);
    }

    @Override
    public TheMendingOfDominariaSecondEffect copy() {
        return new TheMendingOfDominariaSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(
                    controller.getGraveyard().getCards(new FilterLandCard(), source.getSourceId(), source.getControllerId(), game),
                    Zone.BATTLEFIELD, source, game, false, false, false, null
            );
            for (Card card : controller.getGraveyard().getCards(game)) {
                controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD, true, true);
            }
            controller.shuffleLibrary(source, game);
        }
        return false;
    }
}
