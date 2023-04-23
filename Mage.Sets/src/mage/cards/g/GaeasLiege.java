package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class GaeasLiege extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FOREST);
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.FOREST, "");

    static {
        filter2.add(DefendingPlayerControlsPredicate.instance);
    }

    private static final DynamicValue xValue1 = new PermanentsOnBattlefieldCount(filter);
    private static final DynamicValue xValue2 = new PermanentsOnBattlefieldCount(filter2);

    public GaeasLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As long as Gaea's Liege isn't attacking, its power and toughness are each equal to the number of Forests you control. As long as Gaea's Liege is attacking, its power and toughness are each equal to the number of Forests defending player controls.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConditionalContinuousEffect(
                new SetBasePowerToughnessSourceEffect(xValue2),
                new SetBasePowerToughnessSourceEffect(xValue1),
                SourceAttackingCondition.instance, "as long as {this} isn't attacking, " +
                "its power and toughness are each equal to the number of Forests you control. " +
                "As long as {this} is attacking, its power and toughness are each equal " +
                "to the number of Forests defending player controls"
        )));

        // {T}: Target land becomes a Forest until Gaea's Liege leaves the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new BecomesBasicLandTargetEffect(Duration.UntilSourceLeavesBattlefield, SubType.FOREST), new TapSourceCost()
        );
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private GaeasLiege(final GaeasLiege card) {
        super(card);
    }

    @Override
    public GaeasLiege copy() {
        return new GaeasLiege(this);
    }
}
