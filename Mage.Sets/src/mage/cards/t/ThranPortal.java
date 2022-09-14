package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseBasicLandTypeEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.AddChosenSubtypeEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.enterAttribute.EnterAttributeAddChosenSubtypeEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class ThranPortal extends CardImpl {

    public ThranPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        addSubType(SubType.GATE);

        // Thran Portal enters the battlefield tapped unless you control two or fewer other lands.
        Condition controls = new InvertCondition(new PermanentsOnTheBattlefieldCondition(new FilterLandPermanent(), ComparisonType.FEWER_THAN, 3));
        String abilityText = " tapped unless you control two or fewer other lands";
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new TapSourceEffect(), controls, abilityText), abilityText));

        // As Thran Portal enters the battlefield, choose a basic land type.
        // Thran Portal is the chosen type in addition to its other types.
        AsEntersBattlefieldAbility chooseLandTypeAbility = new AsEntersBattlefieldAbility(new ChooseBasicLandTypeEffect(Outcome.AddAbility));
        chooseLandTypeAbility.addEffect(new EnterAttributeAddChosenSubtypeEffect()); // While it enters
        this.addAbility(chooseLandTypeAbility);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AddChosenSubtypeEffect())); // While on the battlefield

        // Mana abilities of Thran Portal cost an additional 1 life to activate.
        // This also adds the mana ability
        Ability ability = new SimpleStaticAbility(new ThranPortalAdditionalCostEffect());
        ability.addEffect(new ThranPortalManaAbilityContinousEffect());
        this.addAbility(ability);
    }

    private ThranPortal(final ThranPortal card) {
        super(card);
    }

    @Override
    public ThranPortal copy() {
        return new ThranPortal(this);
    }
}

class ThranPortalManaAbilityContinousEffect extends ContinuousEffectImpl {

    private static final Map<SubType, BasicManaAbility> abilityMap = new HashMap<SubType, BasicManaAbility>() {{
        put(SubType.PLAINS, new WhiteManaAbility());
        put(SubType.ISLAND, new BlueManaAbility());
        put(SubType.SWAMP, new BlackManaAbility());
        put(SubType.MOUNTAIN, new RedManaAbility());
        put(SubType.FOREST, new GreenManaAbility());
    }};

    public ThranPortalManaAbilityContinousEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "mana abilities of {this} cost an additional 1 life to activate";
    }

    public ThranPortalManaAbilityContinousEffect(final ThranPortalManaAbilityContinousEffect effect) {
        super(effect);
    }

    @Override
    public ThranPortalManaAbilityContinousEffect copy() {
        return new ThranPortalManaAbilityContinousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        switch (choice) {
            case FOREST:
                dependencyTypes.add(DependencyType.BecomeForest);
                break;
            case PLAINS:
                dependencyTypes.add(DependencyType.BecomePlains);
                break;
            case MOUNTAIN:
                dependencyTypes.add(DependencyType.BecomeMountain);
                break;
            case ISLAND:
                dependencyTypes.add(DependencyType.BecomeIsland);
                break;
            case SWAMP:
                dependencyTypes.add(DependencyType.BecomeSwamp);
                break;
            default:
                throw new RuntimeException("Incorrect mana choice " + choice + "for Thran Portal");
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thranPortal = game.getPermanent(source.getSourceId());
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        if (thranPortal == null || choice == null) {
            return false;
        }

        if (!thranPortal.hasSubtype(choice, game)) {
            thranPortal.addSubType(choice);
        }
        if (!thranPortal.hasAbility(abilityMap.get(choice), game)) {
            thranPortal.addAbility(abilityMap.get(choice), source.getId(), game);
        }

        return true;
    }
}

class ThranPortalAdditionalCostEffect extends CostModificationEffectImpl {

    ThranPortalAdditionalCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
    }

    private ThranPortalAdditionalCostEffect(final ThranPortalAdditionalCostEffect effect) {
        super(effect);
    }

    @Override
    public ThranPortalAdditionalCostEffect copy() {
        return new ThranPortalAdditionalCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        abilityToModify.addCost(new PayLifeCost(1));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!abilityToModify.getSourceId().equals(source.getSourceId())) {
            return false;
        }

        return abilityToModify instanceof ManaAbility;
    }
}