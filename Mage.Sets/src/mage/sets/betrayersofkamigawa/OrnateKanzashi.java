
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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class OrnateKanzashi extends CardImpl<OrnateKanzashi> {


    public OrnateKanzashi(UUID ownerId) {
        super(ownerId, 157, "Ornate Kanzashi", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "BOK";

        // {2}, {T}: Target opponent exiles the top card of his or her library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new OrnateKanzashiEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public OrnateKanzashi(final OrnateKanzashi card) {
        super(card);
    }

    @Override
    public OrnateKanzashi copy() {
        return new OrnateKanzashi(this);
    }

}
class OrnateKanzashiEffect extends OneShotEffect<OrnateKanzashiEffect> {

    public OrnateKanzashiEffect() {
        super(Constants.Outcome.Detriment);
        this.staticText = "Target opponent exiles the top card of his or her library. You may play that card this turn";
    }

    public OrnateKanzashiEffect(final OrnateKanzashiEffect effect) {
        super(effect);
    }

    @Override
    public OrnateKanzashiEffect copy() {
        return new OrnateKanzashiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && player.getLibrary().size() > 0) {
            Library library = player.getLibrary();
            Card card = library.removeFromTop(game);
            card.moveToExile(source.getSourceId(), "Ornate Kanzashi", source.getSourceId(), game);

            if (card != null) {
                player.revealCards("Card to play", new CardsImpl(card), game);
                game.addEffect(new OrnateKanzashiCastFromExileEffect(card.getId()), source);
            }
            return true;
        }
        return false;
    }
}

class OrnateKanzashiCastFromExileEffect extends AsThoughEffectImpl<OrnateKanzashiCastFromExileEffect> {

    private UUID cardId;

    public OrnateKanzashiCastFromExileEffect(UUID cardId) {
        super(Constants.AsThoughEffectType.CAST, Constants.Duration.EndOfTurn, Constants.Outcome.Benefit);
        staticText = "You may play card from exile";
        this.cardId = cardId;
    }

    public OrnateKanzashiCastFromExileEffect(final OrnateKanzashiCastFromExileEffect effect) {
        super(effect);
        cardId = effect.cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OrnateKanzashiCastFromExileEffect copy() {
        return new OrnateKanzashiCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(this.cardId)) {
            Card card = game.getCard(this.cardId);
            if (card != null && game.getState().getZone(this.cardId) == Constants.Zone.EXILED) {
                Player player = game.getPlayer(source.getControllerId());
                if (player != null && player.chooseUse(Constants.Outcome.Benefit, "Play this card?", game)) {
                    if (card.getCardType().contains(CardType.LAND)) {
                        // If the revealed card is a land, you can play it only if it's your turn and you haven't yet played a land this turn.
                        if (game.getActivePlayerId().equals(player.getId()) && player.getLandsPlayed() < player.getLandsPerTurn()) {
                            return player.playLand(card, game);
                        }
                    } else {
                        Ability ability = card.getSpellAbility();
                        if (ability != null && ability instanceof SpellAbility) {
                            return player.cast((SpellAbility) ability, game, false);
                        }
                    }
                }
            }
        }
        return false;
    }
}