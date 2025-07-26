package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseBasicLandTypeEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class ThranPortal extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_LESS, 2);

    public ThranPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        addSubType(SubType.GATE);

        // Thran Portal enters the battlefield tapped unless you control two or fewer other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // As Thran Portal enters the battlefield, choose a basic land type.
        AsEntersBattlefieldAbility chooseLandTypeAbility = new AsEntersBattlefieldAbility(new ChooseBasicLandTypeEffect(Outcome.AddAbility));
        chooseLandTypeAbility.addEffect(new ThranPortalAddSubtypeEnteringEffect());
        this.addAbility(chooseLandTypeAbility);

        // Thran Portal is the chosen type in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new ThranPortalManaAbilityContinuousEffect()));

        // Mana abilities of Thran Portal cost an additional 1 life to activate.
        Ability ability = new SimpleStaticAbility(new ThranPortalAdditionalCostEffect());
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

class ThranPortalAddSubtypeEnteringEffect extends OneShotEffect {

    public ThranPortalAddSubtypeEnteringEffect() {
        super(Outcome.Benefit);
    }

    protected ThranPortalAddSubtypeEnteringEffect(final ThranPortalAddSubtypeEnteringEffect effect) {
        super(effect);
    }

    @Override
    public ThranPortalAddSubtypeEnteringEffect copy() {
        return new ThranPortalAddSubtypeEnteringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thranPortal = game.getPermanentEntering(source.getSourceId());
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        if (thranPortal != null && choice != null) {
            thranPortal.addSubType(choice);
            return true;
        }
        return false;
    }
}

class ThranPortalManaAbilityContinuousEffect extends ContinuousEffectImpl {

    private static final Map<SubType, BasicManaAbility> abilityMap = new HashMap<SubType, BasicManaAbility>() {{
        put(SubType.PLAINS, new WhiteManaAbility());
        put(SubType.ISLAND, new BlueManaAbility());
        put(SubType.SWAMP, new BlackManaAbility());
        put(SubType.MOUNTAIN, new RedManaAbility());
        put(SubType.FOREST, new GreenManaAbility());
    }};

    public ThranPortalManaAbilityContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "{this} is the chosen type in addition to its other types.";
    }

    private ThranPortalManaAbilityContinuousEffect(final ThranPortalManaAbilityContinuousEffect effect) {
        super(effect);
    }

    @Override
    public ThranPortalManaAbilityContinuousEffect copy() {
        return new ThranPortalManaAbilityContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        if (choice == null) {
            discard();
            return;
        }

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

class ThranPortalAdditionalCostEffect extends ContinuousEffectImpl {

    ThranPortalAdditionalCostEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "mana abilities of {this} cost an additional 1 life to activate";
    }

    private ThranPortalAdditionalCostEffect(final ThranPortalAdditionalCostEffect effect) {
        super(effect);
    }

    @Override
    public ThranPortalAdditionalCostEffect copy() {
        return new ThranPortalAdditionalCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thranPortal = game.getPermanent(source.getSourceId());
        if (thranPortal == null) {
            return false;
        }
        List<Ability> abilities = thranPortal.getAbilities(game);
        if (abilities.isEmpty()) {
            return false;
        }
        boolean result = false;
        for (Ability ability : abilities) {
            if (ability.isManaAbility()) {
                ability.addCost(new PayLifeCost(1));
                result = true;
            }
        }
        return result;
    }
}
