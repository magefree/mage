package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetCopiableCharacteristicsSourceEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author xenohedron
 */
public class SelectCopiableCharacteristicsSourceEffect extends OneShotEffect {

    private final List<Token> choices;

    /**
     * This effect should be used with "As ... enters the battlefield" and "as ... is turned face up" abilities
     * to set copiable characteristics chosen by the source controller
     *
     * @param choices Tokens with the appropriate characteristics to select from
     */
    public SelectCopiableCharacteristicsSourceEffect(Token... choices) {
        super(Outcome.AddAbility);
        this.choices = Arrays.asList(choices);
        staticText = makeText();
    }

    protected SelectCopiableCharacteristicsSourceEffect(final SelectCopiableCharacteristicsSourceEffect effect) {
        super(effect);
        this.choices = effect.choices;
    }

    @Override
    public SelectCopiableCharacteristicsSourceEffect copy() {
        return new SelectCopiableCharacteristicsSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) {
            permanent = game.getPermanent(source.getSourceId());
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null || controller == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose characteristics for " + permanent.getIdName());
        for (Token token : choices) {
            choice.getChoices().add(token.getDescription());
        }
        if (!controller.choose(Outcome.Neutral, choice, game)) {
            return false;
        }
        Token characteristics = null;
        for (Token token : choices) {
            if (token.getDescription().equals(choice.getChoice())) {
                characteristics = token;
                break;
            }
        }
        if (characteristics == null) {
            return false;
        }
        game.addEffect(new SetCopiableCharacteristicsSourceEffect(characteristics), source);
        return true;
    }

    private String makeText() {
        StringBuilder sb = new StringBuilder("it becomes your choice of ");
        int i = 0;
        int last = choices.size() - 1;
        boolean setType = false;
        for (Token token : choices) {
            i++;
            String description = token.getDescription();
            if (description.contains("creature")) {
                sb.append(CardUtil.addArticle(description));
            } else {
                sb.append(description);
            }
            if (i < last) {
                sb.append(", ");
            } else if (i == last) {
                sb.append(", or ");
            }
            if (!token.getSubtype().isEmpty()) {
                setType = true;
            }
        }
        if (setType) {
            sb.append(" in addition to its other types");
        }
        return sb.toString();
    }

}
