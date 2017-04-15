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
import mage.abilities.effects.OneShotEffect;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class NameACardEffect extends OneShotEffect {

    public static String INFO_KEY = "NAMED_CARD";

    public enum TypeOfName {

        ALL,
        NON_ARTIFACT_AND_NON_LAND_NAME,
        NON_LAND_NAME,
        NON_LAND_AND_NON_CREATURE_NAME,
        CREATURE_NAME,
        ARTIFACT_NAME
    }

    private final TypeOfName typeOfName;

    public NameACardEffect(TypeOfName typeOfName) {
        super(Outcome.Detriment);
        this.typeOfName = typeOfName;
        staticText = setText();
    }

    public NameACardEffect(final NameACardEffect effect) {
        super(effect);
        this.typeOfName = effect.typeOfName;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getPermanentEntering(source.getSourceId());
        if (sourceObject == null) {
            sourceObject = game.getObject(source.getSourceId());
        }
        if (controller != null && sourceObject != null) {
            Choice cardChoice = new ChoiceImpl();
            switch (typeOfName) {
                case ALL:
                    cardChoice.setChoices(CardRepository.instance.getNames());
                    cardChoice.setMessage("Name a card");
                    break;
                case NON_ARTIFACT_AND_NON_LAND_NAME:
                    cardChoice.setChoices(CardRepository.instance.getNonArtifactAndNonLandNames());
                    cardChoice.setMessage("Name a non artifact and non land card");
                    break;
                case NON_LAND_AND_NON_CREATURE_NAME:
                    cardChoice.setChoices(CardRepository.instance.getNonLandAndNonCreatureNames());
                    cardChoice.setMessage("Name a non land and non creature card");
                    break;
                case NON_LAND_NAME:
                    cardChoice.setChoices(CardRepository.instance.getNonLandNames());
                    cardChoice.setMessage("Name a non land card");
                    break;
                case CREATURE_NAME:
                    cardChoice.setChoices(CardRepository.instance.getCreatureNames());
                    cardChoice.setMessage("Name a creature card");
                    break;
                case ARTIFACT_NAME:
                    cardChoice.setChoices(CardRepository.instance.getArtifactNames());
                    cardChoice.setMessage("Name an artifact card");
                    break;
            }
            cardChoice.clearChoice();
            while (!controller.choose(Outcome.Detriment, cardChoice, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }
            String cardName = cardChoice.getChoice();
            if (!game.isSimulation()) {
                game.informPlayers(sourceObject.getLogName() + ", named card: [" + cardName + ']');
            }
            game.getState().setValue(source.getSourceId().toString() + INFO_KEY, cardName);
            if (sourceObject instanceof Permanent) {
                ((Permanent) sourceObject).addInfo(INFO_KEY, CardUtil.addToolTipMarkTags("Named card: " + cardName), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public NameACardEffect copy() {
        return new NameACardEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("name a ");
        switch (typeOfName) {
            case ALL:
                sb.append("card");
                break;
            case NON_ARTIFACT_AND_NON_LAND_NAME:
                sb.append("nonartifact, nonland card");
                break;
            case NON_LAND_AND_NON_CREATURE_NAME:
                sb.append("card other than a creature or a land card");
                break;
            case NON_LAND_NAME:
                sb.append("nonland card");
                break;
            case CREATURE_NAME:
                sb.append("creature card");
                break;
            case ARTIFACT_NAME:
                sb.append("artifact card");
                break;
        }
        return sb.toString();
    }
}
