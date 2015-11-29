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
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Luna Skyrise
 */
public class RevealTargetPlayerLibraryEffect extends OneShotEffect {

    private DynamicValue amountCards;

    public RevealTargetPlayerLibraryEffect(int amountCards) {
        this(new StaticValue(amountCards));
    }

    public RevealTargetPlayerLibraryEffect(DynamicValue amountCards) {
        super(Outcome.Neutral);
        this.amountCards = amountCards;
        this.staticText = setText();
    }

    public RevealTargetPlayerLibraryEffect(final RevealTargetPlayerLibraryEffect effect) {
        super(effect);
        this.amountCards = effect.amountCards;
    }

    @Override
    public RevealTargetPlayerLibraryEffect copy() {
        return new RevealTargetPlayerLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || targetPlayer == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.LIBRARY);
        cards.addAll(player.getLibrary().getTopCards(game, amountCards.calculate(game, source, this)));
        player.revealCards(sourceObject.getIdName() + " - Top " + amountCards.toString() + "cards of " + targetPlayer.getName() + "\'s library", cards, game);
        return true;
    }

    private String setText() {
        return "Reveal the top " + CardUtil.numberToText(amountCards.toString()) + " cards of target player's library.";
    }
}
