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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.NameACardEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class LostLegacy extends CardImpl {

    public LostLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Name a nonartifact, nonland card. Search target player's graveyard, hand and library for any number of cards with that name and exile them. That player shuffles his or her library, then draws a card for each card exiled from hand this way.
        this.getSpellAbility().addEffect((new NameACardEffect(NameACardEffect.TypeOfName.NON_ARTIFACT_AND_NON_LAND_NAME)));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new LostLegacyEffect());
    }

    public LostLegacy(final LostLegacy card) {
        super(card);
    }

    @Override
    public LostLegacy copy() {
        return new LostLegacy(this);
    }
}

class LostLegacyEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    LostLegacyEffect() {
        super(true, "target player's", "any number of cards with that name");
    }

    LostLegacyEffect(final LostLegacyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null && cardName != null && !cardName.isEmpty()) {
            FilterCard filter = new FilterCard();
            filter.add(new NamePredicate(cardName));
            int cardsInHandBefore = targetPlayer.getHand().count(filter, game);
            boolean result = super.applySearchAndExile(game, source, cardName, targetPointer.getFirst(game, source));
            int cardsExiled = cardsInHandBefore - targetPlayer.getHand().count(filter, game);
            if (cardsExiled > 0) {
                targetPlayer.drawCards(cardsExiled, game);
            }
            return result;
        }
        return false;
    }

    @Override
    public LostLegacyEffect copy() {
        return new LostLegacyEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Search target player's graveyard, hand and library for any number of cards with that name and exile them. That player shuffles his or her library, then draws a card for each card exiled from hand this way";
    }
}
