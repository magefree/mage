package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class ChooseCreatureTypeEffect extends OneShotEffect {

    private final List<SubType> subTypes = new ArrayList<>();

    public ChooseCreatureTypeEffect(Outcome outcome, SubType... subTypes) {
        super(outcome);
        for (SubType subType : subTypes) {
            this.subTypes.add(subType);
        }
        if (this.subTypes.isEmpty()) {
            staticText = "choose a creature type";
        } else {
            staticText = "choose " + CardUtil.concatWithOr(this.subTypes);
        }
    }

    protected ChooseCreatureTypeEffect(final ChooseCreatureTypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (controller == null || mageObject == null) {
            return false;
        }
        Choice typeChoice = new ChoiceCreatureType(game, source, true, "Choose a creature type", subTypes);
        if (!controller.choose(outcome, typeChoice, game)) {
            return false;
        }
        game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + typeChoice.getChoiceKey());
        game.getState().setValue(source.getSourceId() + "_type", SubType.byDescription(typeChoice.getChoiceKey()));
        if (mageObject instanceof Permanent) {
            ((Permanent) mageObject).addInfo("chosen type", CardUtil.addToolTipMarkTags("Chosen type: " + typeChoice.getChoiceKey()), game);
        }
        return true;
    }

    @Override
    public ChooseCreatureTypeEffect copy() {
        return new ChooseCreatureTypeEffect(this);
    }

    public static SubType getChosenCreatureType(UUID objectId, Game game) {
        return getChosenCreatureType(objectId, game, "_type");
    }

    /**
     * @param objectId    sourceId the effect was executed under
     * @param game
     * @param typePostfix special postfix if you want to store multiple choices
     *                    from different effects
     * @return
     */
    public static SubType getChosenCreatureType(UUID objectId, Game game, String typePostfix) {
        SubType creatureType = null;
        Object savedCreatureType = game.getState().getValue(objectId + typePostfix);
        if (savedCreatureType != null) {
            creatureType = SubType.byDescription(savedCreatureType.toString());
        }
        return creatureType;
    }
}
