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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class ToothAndNail extends CardImpl<ToothAndNail> {

    public ToothAndNail(UUID ownerId) {
        super(ownerId, 170, "Tooth and Nail", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{G}{G}");
        this.expansionSetCode = "MMA";

        this.color.setGreen(true);

        // Choose one - 
        // Search your library for up to two creature cards, reveal them, put them into your hand, then shuffle your library;
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 2, new FilterCreatureCard())));
        // or put up to two creature cards from your hand onto the battlefield.
        Mode mode = new Mode();
        mode.getEffects().add(new ToothAndNailPutCreatureOnBattlefieldEffect());
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}
        this.addAbility(new EntwineAbility("{2}"));
    }

    public ToothAndNail(final ToothAndNail card) {
        super(card);
    }

    @Override
    public ToothAndNail copy() {
        return new ToothAndNail(this);
    }
}

class ToothAndNailPutCreatureOnBattlefieldEffect extends OneShotEffect<ToothAndNailPutCreatureOnBattlefieldEffect> {

    public ToothAndNailPutCreatureOnBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put up to two creature cards from your hand onto the battlefield";
    }

    public ToothAndNailPutCreatureOnBattlefieldEffect(final ToothAndNailPutCreatureOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public ToothAndNailPutCreatureOnBattlefieldEffect copy() {
        return new ToothAndNailPutCreatureOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(0, 2, new FilterCreatureCard("creature cards"));
        if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
            for (UUID targetId: target.getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                }
            }
            return true;
        }
        return false;
    }
}
