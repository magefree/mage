package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceCardType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author emerald000
 */
public class ChooseCardTypeEffect extends OneShotEffect {

    List<CardType> cardTypes = Arrays.stream(CardType.values()).collect(Collectors.toList());

    public ChooseCardTypeEffect(Outcome outcome) {
        this(outcome, Arrays.stream(CardType.values()).collect(Collectors.toList()));
    }

    public ChooseCardTypeEffect(Outcome outcome, List<CardType> cardTypes) {
        super(outcome);
        this.staticText = "choose a card type";
        this.cardTypes = new ArrayList<>(cardTypes);
    }

    private ChooseCardTypeEffect(final ChooseCardTypeEffect effect) {
        super(effect);
        this.cardTypes = new ArrayList<>(effect.cardTypes);
    }

    @Override
    public ChooseCardTypeEffect copy() {
        return new ChooseCardTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (controller != null && mageObject != null) {
            Choice typeChoice = new ChoiceCardType(true, cardTypes);
            if (controller.choose(outcome, typeChoice, game)) {
                game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen: " + typeChoice.getChoice());
                game.getState().setValue(source.getSourceId() + "_type", typeChoice.getChoice());
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosen type", CardUtil.addToolTipMarkTags("Chosen type: " + typeChoice.getChoice()), game);
                }
                return true;
            }
        }
        return false;
    }
}
