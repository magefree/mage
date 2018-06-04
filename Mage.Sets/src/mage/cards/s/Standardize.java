
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author EvilGeek
 */
public final class Standardize extends CardImpl {

    public Standardize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Choose a creature type other than Wall. Each creature becomes that type until end of turn.
        this.getSpellAbility().addEffect(new StandardizeEffect());
    }

    public Standardize(final Standardize card) {
        super(card);
    }

    @Override
    public Standardize copy() {
        return new Standardize(this);
    }
}

class StandardizeEffect extends OneShotEffect {

    public StandardizeEffect() {
        super(Outcome.BoostCreature);
        staticText = "choose a creature type other than Wall. Each creature becomes that type until end of turn";

    }

    public StandardizeEffect(final StandardizeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        String chosenType = "";
        if (player != null && sourceObject != null) {
            Choice typeChoice = new ChoiceCreatureType(sourceObject);
            typeChoice.setMessage("Choose a creature type other than Wall");
            typeChoice.getChoices().remove("Wall");
            if (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                return false;
            }
            game.informPlayers(sourceObject.getLogName() + ": " + player.getLogName() + " has chosen " + typeChoice.getChoice());
            chosenType = typeChoice.getChoice();
            if (chosenType != null && !chosenType.isEmpty()) {
                // ADD TYPE TO TARGET
                ContinuousEffect effect = new BecomesSubtypeAllEffect(Duration.EndOfTurn, SubType.byDescription(chosenType));
                game.addEffect(effect, source);
                return true;
            }

        }
        return false;
    }

    @Override
    public Effect copy() {
        return new StandardizeEffect(this);
    }
}
