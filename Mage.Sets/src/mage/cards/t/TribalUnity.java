
package mage.cards.t;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 * @author anonymous
 */
public final class TribalUnity extends CardImpl {

    public TribalUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{2}{G}");

        // Creatures of the creature type of your choice get +X/+X until end of turn.
        this.getSpellAbility().addEffect(new TribalUnityEffect(ManacostVariableValue.REGULAR));
    }

    private TribalUnity(final TribalUnity card) {
        super(card);
    }

    @Override
    public TribalUnity copy() {
        return new TribalUnity(this);
    }
}

class TribalUnityEffect extends OneShotEffect {

    protected DynamicValue amount;

    public TribalUnityEffect(DynamicValue amount) {
        super(Outcome.UnboostCreature);
        staticText = "Creatures of the creature type of your choice get +X/+X until end of turn.";
        this.amount = amount;
    }

    public TribalUnityEffect(final TribalUnityEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        Choice typeChoice = new ChoiceCreatureType(sourceObject);
        if (player != null && player.choose(outcome, typeChoice, game)) {
            int boost = amount.calculate(game, source, this);
            if (typeChoice.getChoice() != null) {
                game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
            }
            FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent();
            filterCreaturePermanent.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
            game.addEffect(new BoostAllEffect(
                    boost, boost, Duration.EndOfTurn, filterCreaturePermanent, false), source);
            return true;
        }
        return false;
    }

    @Override
    public TribalUnityEffect copy() {
        return new TribalUnityEffect(this);
    }
}
