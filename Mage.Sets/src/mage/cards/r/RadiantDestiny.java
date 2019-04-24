
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllOfChosenSubtypeEffect;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class RadiantDestiny extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public RadiantDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Ascend
        this.addAbility(new AscendAbility());

        // As Radiant Destiny enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +1/+1. As long as you have the city's blessing, they also have vigilance.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllOfChosenSubtypeEffect(1, 1, Duration.WhileOnBattlefield, filter, true));
        ContinuousEffect effect = new ConditionalContinuousEffect(
                new GainAbilityAllOfChosenSubtypeEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, FILTER_PERMANENT_CREATURES_CONTROLLED),
                CitysBlessingCondition.instance,
                "As long as you have the city's blessing, they also have vigilance.");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public RadiantDestiny(final RadiantDestiny card) {
        super(card);
    }

    @Override
    public RadiantDestiny copy() {
        return new RadiantDestiny(this);
    }
}
