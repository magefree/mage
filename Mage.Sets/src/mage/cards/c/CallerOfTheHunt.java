package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.targetpointer.FixedTarget;

/**
 * @author jeffwadsworth
 */
public final class CallerOfTheHunt extends CardImpl {

    FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public CallerOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);

        // As an additional cost to cast Caller of the Hunt, choose a creature type.
        // Caller of the Hunt's power and toughness are each equal to the number of creatures of the chosen type on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("as an additional cost to cast this spell, choose a creature type. \r"
                + "{this}'s power and toughness are each equal to the number of creatures of the chosen type on the battlefield")));

        this.getSpellAbility().setCostAdjuster(CallerOfTheHuntAdjuster.instance);

    }

    public CallerOfTheHunt(final CallerOfTheHunt card) {
        super(card);
    }

    @Override
    public CallerOfTheHunt copy() {
        return new CallerOfTheHunt(this);
    }
}

enum CallerOfTheHuntAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        MageObject mageObject = game.getObject(ability.getSourceId());
        Effect effect = new ChooseCreatureTypeEffect(Outcome.Benefit);
        if (mageObject != null) {
            effect.apply(game, ability);
        }
        if (mageObject != null) {
            SubType typeChoice = (SubType) game.getState().getValue(mageObject.getId() + "_type");
            if (typeChoice != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("chosen creature type");
                filter.add(new SubtypePredicate(typeChoice));
                ContinuousEffect effectPowerToughness = new SetPowerToughnessSourceEffect(
                        new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame);
                effectPowerToughness.setText("");
                SimpleStaticAbility sa = new SimpleStaticAbility(Zone.ALL, effectPowerToughness);
                GainAbilityTargetEffect effectTest = new GainAbilityTargetEffect(sa, Duration.EndOfGame);
                effectTest.setTargetPointer(new FixedTarget(ability.getSourceId()));
                game.getState().addEffect(effectTest, ability);
            }
        }
    }
}

class ChooseCreatureTypeEffect extends OneShotEffect {

    public ChooseCreatureTypeEffect(Outcome outcome) {
        super(outcome);
        staticText = "choose a creature type";
    }

    public ChooseCreatureTypeEffect(final ChooseCreatureTypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        Choice typeChoice = new ChoiceCreatureType(mageObject);
        if (controller != null
                && mageObject != null
                && controller.choose(outcome, typeChoice, game)) {
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getName() + ": "
                        + controller.getLogName() + " has chosen " + typeChoice.getChoice());
            }
            game.getState().setValue(mageObject.getId()
                    + "_type", SubType.byDescription(typeChoice.getChoice()));
            return true;
        }
        return false;
    }

    @Override
    public ChooseCreatureTypeEffect copy() {
        return new ChooseCreatureTypeEffect(this);
    }
}
