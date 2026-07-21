package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MoreThanStartingLifeTotalCondition;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.replacement.GainDoubleLifeReplacementEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class DoctorStrangeSurgeon extends CardImpl {

    public DoctorStrangeSurgeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(new GainDoubleLifeReplacementEffect()));

        // At the beginning of each combat, if you have at least 10 life more than your starting life total, creatures you control get +2/+2 and gain vigilance until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(TargetController.ANY,
                new BoostControlledEffect(2, 2, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES), false)
            .withInterveningIf(MoreThanStartingLifeTotalCondition.TEN);
        ability.addEffect(new GainAbilityControlledEffect(
            VigilanceAbility.getInstance(),
            Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
            .setText("and gain vigilance until end of turn"));
        this.addAbility(ability);
    }

    private DoctorStrangeSurgeon(final DoctorStrangeSurgeon card) {
        super(card);
    }

    @Override
    public DoctorStrangeSurgeon copy() {
        return new DoctorStrangeSurgeon(this);
    }
}
