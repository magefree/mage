package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ControllerSpeedCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamutTheDrivingForce extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature spells");
    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public SamutTheDrivingForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Other creatures you control get +X/+0, where X is your speed.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                ControllerSpeedCount.instance, StaticValue.get(0), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));

        // Noncreature spells you cast cost {X} less to cast, where X is your speed.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filter, ControllerSpeedCount.instance)
                        .setText("noncreature spells you cast cost {X} less to cast, where X is your speed")
        ));
    }

    private SamutTheDrivingForce(final SamutTheDrivingForce card) {
        super(card);
    }

    @Override
    public SamutTheDrivingForce copy() {
        return new SamutTheDrivingForce(this);
    }
}
