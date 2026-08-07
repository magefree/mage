package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.*;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author anonymous
 */
public final class TsaganRaiderWarlord extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with first strike or double strike");

    static {
        filter.add(Predicates.or(new AbilityPredicate(FirstStrikeAbility.class), new AbilityPredicate(DoubleStrikeAbility.class)));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public TsaganRaiderWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Whenever Tsagan attacks, creatures you control get +1/+0 until end of turn for each creature you control with first strike or double strike.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(xValue, StaticValue.get(0), Duration.EndOfTurn)));

        // Max speed -- Tsagan has deathtouch. Other creatures you control have first strike.
        Ability ability = new SimpleStaticAbility(new GainAbilitySourceEffect(DeathtouchAbility.getInstance()));
        ability.addEffect(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES,
                true
        ));
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private TsaganRaiderWarlord(final TsaganRaiderWarlord card) {
        super(card);
    }

    @Override
    public TsaganRaiderWarlord copy() {
        return new TsaganRaiderWarlord(this);
    }
}
