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
package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class EpicExperiment extends CardImpl {

    public EpicExperiment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{R}");

        // Exile the top X cards of your library. For each instant and sorcery card with
        // converted mana cost X or less among them, you may cast that card without paying
        // its mana cost. Then put all cards exiled this way that weren't cast into your graveyard.
        this.getSpellAbility().addEffect(new EpicExperimentEffect());
    }

    public EpicExperiment(final EpicExperiment card) {
        super(card);
    }

    @Override
    public EpicExperiment copy() {
        return new EpicExperiment(this);
    }
}

class EpicExperimentEffect extends OneShotEffect {

    public EpicExperimentEffect() {
        super(Outcome.PlayForFree);
        staticText = "Exile the top X cards of your library. For each instant and sorcery card with converted mana cost X or less among them, you may cast that card without paying its mana cost. Then put all cards exiled this way that weren't cast into your graveyard";
    }

    public EpicExperimentEffect(final EpicExperimentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            // move cards from library to exile
            controller.moveCardsToExile(controller.getLibrary().getTopCards(game, source.getManaCostsToPay().getX()), source, game, true, source.getSourceId(), sourceObject.getIdName());
            // cast the possible cards without paying the mana
            ExileZone epicExperimentExileZone = game.getExile().getExileZone(source.getSourceId());
            FilterCard filter = new FilterInstantOrSorceryCard();
            filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));
            filter.setMessage("instant and sorcery cards with converted mana cost " + source.getManaCostsToPay().getX() + " or less");
            Cards cardsToCast = new CardsImpl();
            if (epicExperimentExileZone == null) {
                return true;
            }
            cardsToCast.addAll(epicExperimentExileZone.getCards(filter, source.getSourceId(), source.getControllerId(), game));
            while (!cardsToCast.isEmpty()) {
                if (!controller.chooseUse(Outcome.PlayForFree, "Cast (another) a card exiled with " + sourceObject.getLogName() + " without paying its mana cost?", source, game)) {
                    break;
                }
                TargetCard targetCard = new TargetCard(1, Zone.EXILED, new FilterCard("instant or sorcery card to cast for free"));
                if (controller.choose(Outcome.PlayForFree, cardsToCast, targetCard, game)) {
                    Card card = game.getCard(targetCard.getFirstTarget());
                    if (card != null) {
                        if (controller.cast(card.getSpellAbility(), game, true)) {
                            cardsToCast.remove(card);
                        } else {
                            game.informPlayer(controller, "You're not able to cast " + card.getIdName() + " or you canceled the casting.");
                        }
                    }
                }
            }
            // move cards not cast to graveyard
            ExileZone exileZone = game.getExile().getExileZone(source.getSourceId());
            if (exileZone != null) {
                controller.moveCards(exileZone.getCards(game), Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public EpicExperimentEffect copy() {
        return new EpicExperimentEffect(this);
    }
}
