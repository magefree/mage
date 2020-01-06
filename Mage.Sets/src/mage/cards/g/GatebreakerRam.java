package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.GateYouControlHint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GatebreakerRam extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(filter);
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public GatebreakerRam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SHEEP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Gatebreaker Ram gets +1/+1 for each Gate you control.
        this.addAbility(new SimpleStaticAbility(
                new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)
                        .setText("{this} gets +1/+1 for each Gate you control.")
        ).addHint(GateYouControlHint.instance));

        // As long as you control two or more Gates, Gatebreaker Ram has vigilance and trample.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance()),
                condition, "As long as you control two or more Gates, {this} has vigilance"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                condition, "and trample"
        ));
        this.addAbility(ability);
    }

    private GatebreakerRam(final GatebreakerRam card) {
        super(card);
    }

    @Override
    public GatebreakerRam copy() {
        return new GatebreakerRam(this);
    }
}
