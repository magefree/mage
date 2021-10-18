package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

public class BecomesChosenCreatureTypeTargetEffect extends OneShotEffect {

    private final boolean nonWall;
    private final Duration duration;

    public BecomesChosenCreatureTypeTargetEffect() {
        this(false, Duration.EndOfTurn);
    }

    public BecomesChosenCreatureTypeTargetEffect(boolean nonWall) {
        this(nonWall, Duration.EndOfTurn);
    }

    public BecomesChosenCreatureTypeTargetEffect(boolean nonWall, Duration duration) {
        super(Outcome.BoostCreature);
        this.nonWall = nonWall;
        this.duration = duration;
        if(nonWall) {
            staticText = "choose a creature type other than Wall. Target creature becomes that type until end of turn";
        }
        else {
            staticText = "target creature becomes the creature type of your choice until end of turn";
        }

    }

    public BecomesChosenCreatureTypeTargetEffect(final BecomesChosenCreatureTypeTargetEffect effect) {
        super(effect);
        this.nonWall = effect.nonWall;
        this.duration = effect.duration;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        String chosenType = "";
        if (player != null && card != null) {
            Choice typeChoice = new ChoiceCreatureType();
            String msg = "Choose a creature type";
            if(nonWall) {
                msg += " other than Wall";
            }
            typeChoice.setMessage(msg);
            if(nonWall) {
                typeChoice.getChoices().remove(SubType.WALL.getDescription());
            }
            while (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            game.informPlayers(card.getName() + ": " + player.getLogName() + " has chosen " + typeChoice.getChoice());
            chosenType = typeChoice.getChoice();
            if (chosenType != null && !chosenType.isEmpty()) {
                // ADD TYPE TO TARGET
                ContinuousEffect effect = new BecomesCreatureTypeTargetEffect(duration, SubType.byDescription(chosenType));
                effect.setTargetPointer(new FixedTarget(getTargetPointer().getFirst(game, source), game));
                game.addEffect(effect, source);
                return true;
            }

        }
        return false;
    }

    @Override
    public Effect copy() {
        return new BecomesChosenCreatureTypeTargetEffect(this);
    }

}
