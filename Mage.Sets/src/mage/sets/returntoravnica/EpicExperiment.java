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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;


/**
 *
 * @author LevelX2
 */
public class EpicExperiment extends CardImpl<EpicExperiment> {

    public EpicExperiment(UUID ownerId) {
        super(ownerId, 159, "Epic Experiment", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{X}{U}{R}");
        this.expansionSetCode = "RTR";

        this.color.setBlue(true);
        this.color.setRed(true);

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

class EpicExperimentEffect extends OneShotEffect<EpicExperimentEffect> {

    private final static FilterCard filter = new FilterCard();
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                                 new CardTypePredicate(CardType.SORCERY)));
    }

    public EpicExperimentEffect() {
        super(Constants.Outcome.PlayForFree);
        staticText = "Exile the top X cards of your library. For each instant and sorcery card with converted mana cost X or less among them, you may cast that card without paying its mana cost. Then put all cards exiled this way that weren't cast into your graveyard";
    }

    public EpicExperimentEffect(final EpicExperimentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            // move cards from library to exile
            for (int i = 0; i < source.getManaCostsToPay().getX(); i++) {
                if (player != null && player.getLibrary().size() > 0) {
                    Card topCard = player.getLibrary().getFromTop(game);
                    topCard.moveToExile(source.getSourceId(), "Cards exiled by Epic Experiment", source.getId(), game);
                }
            }
            // cast the possible cards without paying the mana
            ExileZone epicExperimentExileZone = game.getExile().getExileZone(source.getSourceId());
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, source.getManaCostsToPay().getX() + 1));
            filter.setMessage("instant and sorcery cards with converted mana cost "+ source.getManaCostsToPay().getX() +" or less");
            while (player != null && epicExperimentExileZone != null && epicExperimentExileZone.count(filter, game) > 0
                    && player.chooseUse(Outcome.PlayForFree, "Cast cards exiled with Epic Experiment without paying its mana cost?", game)) {
                TargetCardInExile target = new TargetCardInExile(filter, source.getSourceId());
                while (epicExperimentExileZone.count(filter, game) > 0 && player.choose(Outcome.PlayForFree, epicExperimentExileZone, target, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            
                            if (player.cast(card.getSpellAbility(), game, true))
                            {
                                game.getExile().removeCard(card, game);
                            }
                        }
                        target.clearChosen();
                }
            }
            // move not casted cards to graveyard
            UUID exileId = source.getSourceId();
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null) {
                exile = exile.copy();
                for (UUID cardId : exile) {
                    Card card = game.getCard(cardId);
                    card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                }
                game.getExile().getExileZone(exileId).clear();

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