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
 * @author LevelX2
 */
public class ChooseACardNameEffect extends OneShotEffect {

    public static final String INFO_KEY = "NAMED_CARD";

    public enum TypeOfName {
        ALL,
        NOT_BASIC_LAND_NAME,
        NONBASIC_LAND_NAME,
        NON_ARTIFACT_AND_NON_LAND_NAME,
        NON_LAND_NAME,
        NON_LAND_AND_NON_CREATURE_NAME,
        CREATURE_NAME,
        ARTIFACT_NAME
    }

    private final TypeOfName typeOfName;

    public ChooseACardNameEffect(TypeOfName typeOfName) {
        super(Outcome.Detriment);
        this.typeOfName = typeOfName;
        staticText = setText();
    }

    public ChooseACardNameEffect(final ChooseACardNameEffect effect) {
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
            Choice cardChoice = new ChoiceImpl(true);
            switch (typeOfName) {
                case ALL:
                    cardChoice.setChoices(CardRepository.instance.getNames());
                    cardChoice.setMessage("Choose a card name");
                    break;
                case NOT_BASIC_LAND_NAME:
                    cardChoice.setChoices(CardRepository.instance.getNotBasicLandNames());
                    cardChoice.setMessage("Choose a card name other than a basic land card name");
                    break;
                case NONBASIC_LAND_NAME:
                    cardChoice.setChoices(CardRepository.instance.getNonbasicLandNames());
                    cardChoice.setMessage("Choose a nonbasic land card name");
                    break;
                case NON_ARTIFACT_AND_NON_LAND_NAME:
                    cardChoice.setChoices(CardRepository.instance.getNonArtifactAndNonLandNames());
                    cardChoice.setMessage("Choose a nonartifact, nonland card name");
                    break;
                case NON_LAND_AND_NON_CREATURE_NAME:
                    cardChoice.setChoices(CardRepository.instance.getNonLandAndNonCreatureNames());
                    cardChoice.setMessage("Choose a nonland and non creature card");
                    break;
                case NON_LAND_NAME:
                    cardChoice.setChoices(CardRepository.instance.getNonLandNames());
                    cardChoice.setMessage("Choose a nonland card name");
                    break;
                case CREATURE_NAME:
                    cardChoice.setChoices(CardRepository.instance.getCreatureNames());
                    cardChoice.setMessage("Choose a creature card name");
                    break;
                case ARTIFACT_NAME:
                    cardChoice.setChoices(CardRepository.instance.getArtifactNames());
                    cardChoice.setMessage("Choose an artifact card name");
                    break;
            }
            cardChoice.clearChoice();
            if (controller.choose(Outcome.Detriment, cardChoice, game)) {
                String cardName = cardChoice.getChoice();
                if (!game.isSimulation()) {
                    game.informPlayers(sourceObject.getLogName() + ", chosen name: [" + cardName + ']');
                }
                game.getState().setValue(source.getSourceId().toString() + INFO_KEY, cardName);
                if (sourceObject instanceof Permanent) {
                    ((Permanent) sourceObject).addInfo(INFO_KEY, CardUtil.addToolTipMarkTags("Chosen name: " + cardName), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ChooseACardNameEffect copy() {
        return new ChooseACardNameEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("choose a ");
        switch (typeOfName) {
            case ALL:
                sb.append("card");
                break;
            case NOT_BASIC_LAND_NAME:
                sb.append("card name other than a basic land card");
                break;
            case NONBASIC_LAND_NAME:
                sb.append("nonbasic land card name");
                break;
            case NON_ARTIFACT_AND_NON_LAND_NAME:
                sb.append("nonartifact, nonland card");
                break;
            case NON_LAND_AND_NON_CREATURE_NAME:
                sb.append("noncreature, nonland card");
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
        sb.append(" name");
        return sb.toString();
    }
}
