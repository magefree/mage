package mage.abilities.effects.common.continuous;

import java.util.Set;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

public class BecomesChosenCreatureTypeTargetEffect extends OneShotEffect {

    private final boolean nonWall;

    public BecomesChosenCreatureTypeTargetEffect() {
        this(false);
    }

    public BecomesChosenCreatureTypeTargetEffect(boolean nonWall) {
        super(Outcome.BoostCreature);
        this.nonWall = nonWall;
        if(nonWall) {
            staticText = "choose a creature type other than wall, target creature's type becomes that type until end of turn";
        }
        else {
            staticText = "target creature becomes the creature type of your choice until end of turn";
        }

    }

    public BecomesChosenCreatureTypeTargetEffect(final BecomesChosenCreatureTypeTargetEffect effect) {
        super(effect);
        this.nonWall = effect.nonWall;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        String chosenType = "";
        if (player != null && card != null) {
            Choice typeChoice = new ChoiceImpl(true);
            String msg = "Choose a creature type";
            if(nonWall) {
                msg += " other than Wall";
            }
            typeChoice.setMessage(msg);
            Set<String> types = CardRepository.instance.getCreatureTypes();
            if(nonWall) {
                types.remove("Wall");
            }
            typeChoice.setChoices(types);
            while (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            game.informPlayers(card.getName() + ": " + player.getLogName() + " has chosen " + typeChoice.getChoice());
            chosenType = typeChoice.getChoice();
            if (chosenType != null && !chosenType.isEmpty()) {
                // ADD TYPE TO TARGET
                ContinuousEffect effect = new BecomesSubtypeTargetEffect(Duration.EndOfTurn, chosenType);
                effect.setTargetPointer(new FixedTarget(getTargetPointer().getFirst(game, source)));
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
