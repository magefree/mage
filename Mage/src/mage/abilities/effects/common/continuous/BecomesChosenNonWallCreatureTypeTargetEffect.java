package mage.abilities.effects.common.continuous;

import java.util.Set;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

public class BecomesChosenNonWallCreatureTypeTargetEffect extends OneShotEffect {

	public BecomesChosenNonWallCreatureTypeTargetEffect() {
		super(Outcome.BoostCreature);
		staticText = "choose a creature type other than wall, target creature's type becomes that type until end of turn";
	
	}
	
	public BecomesChosenNonWallCreatureTypeTargetEffect(final BecomesChosenNonWallCreatureTypeTargetEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        String chosenType = "";
        if (player != null && permanent != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose creature type other than Wall");
            Set<String> types = CardRepository.instance.getCreatureTypes();
            types.remove("Wall");
            typeChoice.setChoices(types);
            while (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            game.informPlayers(permanent.getName() + ": " + player.getLogName() + " has chosen " + typeChoice.getChoice());
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
		return new BecomesChosenNonWallCreatureTypeTargetEffect(this);
	}

}
