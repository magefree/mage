package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jeffwadsworth, JayDi85
 */
public final class CallerOfTheHunt extends CardImpl {

    public CallerOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);

        // As an additional cost to cast Caller of the Hunt, choose a creature type.
        // Caller of the Hunt's power and toughness are each equal to the number of creatures of the chosen type on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("as an additional cost to cast this spell, choose a creature type. \r"
                + "{this}'s power and toughness are each equal to the number of creatures of the chosen type on the battlefield")));

        this.getSpellAbility().setCostAdjuster(CallerOfTheHuntAdjuster.instance);

    }

    private CallerOfTheHunt(final CallerOfTheHunt card) {
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
        if (game.inCheckPlayableState()) {
            return;
        }

        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }

        MageObject sourceObject = game.getObject(ability.getSourceId());
        if (sourceObject == null) {
            return;
        }

        // AI hint - find best creature type with max permanents, all creature type supports too
        Map<SubType, Integer> usedSubTypeStats = new HashMap<>();
        game.getBattlefield().getActivePermanents(ability.getControllerId(), game)
                .stream()
                .map(permanent -> permanent.getSubtype(game))
                .flatMap(Collection::stream)
                .distinct()
                .forEach(subType -> {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent();
                    filter.add(subType.getPredicate());
                    int amount = new PermanentsOnBattlefieldCount(filter).calculate(game, ability, null);
                    usedSubTypeStats.put(subType, amount);
                });
        int maxAmount = 0;
        SubType maxSubType = null;
        for (Map.Entry<SubType, Integer> entry : usedSubTypeStats.entrySet()) {
            if (entry.getValue() > maxAmount) {
                maxSubType = entry.getKey();
                maxAmount = entry.getValue();
            }
        }

        // choose creature type
        SubType typeChoice;
        if (controller.isComputer()) {
            // AI hint - simulate type choose
            game.getState().setValue(sourceObject.getId() + "_type", maxSubType);
        } else {
            // human choose
            Effect effect = new ChooseCreatureTypeEffect(Outcome.Benefit);
            effect.apply(game, ability);
        }
        typeChoice = (SubType) game.getState().getValue(sourceObject.getId() + "_type");
        if (typeChoice == null) {
            return;
        }

        // apply boost
        FilterCreaturePermanent filter = new FilterCreaturePermanent("chosen creature type");
        filter.add(typeChoice.getPredicate());
        ContinuousEffect effectPowerToughness = new SetBasePowerToughnessSourceEffect(
                new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame);
        effectPowerToughness.setText("");
        SimpleStaticAbility setPT = new SimpleStaticAbility(Zone.ALL, effectPowerToughness);
        GainAbilityTargetEffect gainAbility = new GainAbilityTargetEffect(setPT, Duration.EndOfGame);
        gainAbility.setTargetPointer(new FixedTarget(ability.getSourceId()));
        game.getState().addEffect(gainAbility, ability);
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
        MageObject mageObject = game.getObject(source);
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
