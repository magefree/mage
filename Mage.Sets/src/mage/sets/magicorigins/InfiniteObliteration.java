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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class InfiniteObliteration extends CardImpl {

    public InfiniteObliteration(UUID ownerId) {
        super(ownerId, 103, "Infinite Obliteration", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        this.expansionSetCode = "ORI";

        // Name a creature card.  Search target opponent's graveyard, hand, and library
        // for any number of cards with that name and exile them.  Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new InfiniteObliterationEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public InfiniteObliteration(final InfiniteObliteration card) {
        super(card);
    }

    @Override
    public InfiniteObliteration copy() {
        return new InfiniteObliteration(this);
    }
}

class InfiniteObliterationEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public InfiniteObliterationEffect() {
        super(true, "target opponent's", "any number of cards with that name");
    }

    public InfiniteObliterationEffect(final InfiniteObliterationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getCreatureNames());
            cardChoice.clearChoice();
            cardChoice.setMessage("Name a creature card");

            while (!controller.choose(Outcome.Exile, cardChoice, game)) {
                if (!controller.isInGame()) {
                    return false;
                }
            }
            String cardName;
            cardName = cardChoice.getChoice();
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (sourceObject != null) {
                game.informPlayers(sourceObject.getName() + " named card: [" + cardName + "]");
            }

            super.applySearchAndExile(game, source, cardName, player.getId());
        }
        return true;
    }

    @Override
    public InfiniteObliterationEffect copy() {
        return new InfiniteObliterationEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name a creature card. ");
        sb.append(super.getText(mode));
        return sb.toString();
    }

}
