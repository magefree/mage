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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class StainTheMind extends CardImpl {

    public StainTheMind(UUID ownerId) {
        super(ownerId, 117, "Stain the Mind", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{B}");
        this.expansionSetCode = "M15";

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Name a nonland card. Search target player's graveyard, hand, and library for any number of card's with that name and exile them. Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new StainTheMindEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public StainTheMind(final StainTheMind card) {
        super(card);
    }

    @Override
    public StainTheMind copy() {
        return new StainTheMind(this);
    }
}

class StainTheMindEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public StainTheMindEffect() {
        super(true, "target player's", "any number of cards with that name");
    }

    public StainTheMindEffect(final StainTheMindEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNonLandNames());
            cardChoice.clearChoice();
            cardChoice.setMessage("Name a nonland card");

            while (!controller.choose(Outcome.Exile, cardChoice, game)) {
                if (!controller.canRespond()) {
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
    public StainTheMindEffect copy() {
        return new StainTheMindEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name a nonland card. ");
        sb.append(super.getText(mode));
        return sb.toString();
    }

}
