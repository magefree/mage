package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllOfChosenSubtypeEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED;

/**
 * @author LevelX2
 */
public final class RadiantDestiny extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RadiantDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Ascend
        this.addAbility(new AscendAbility());

        // As Radiant Destiny enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +1/+1. As long as you have the city's blessing, they also have vigilance.
        Ability ability = new SimpleStaticAbility(new BoostAllOfChosenSubtypeEffect(1, 1, Duration.WhileOnBattlefield, filter, false));
        ContinuousEffect effect = new ConditionalContinuousEffect(
                new GainAbilityAllOfChosenSubtypeEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, FILTER_PERMANENT_CREATURES_CONTROLLED),
                CitysBlessingCondition.instance,
                "As long as you have the city's blessing, they also have vigilance.");
        ability.addEffect(effect);
        ability.addHint(CitysBlessingHint.instance);
        this.addAbility(ability);
    }

    private RadiantDestiny(final RadiantDestiny card) {
        super(card);
    }

    @Override
    public RadiantDestiny copy() {
        return new RadiantDestiny(this);
    }
}
