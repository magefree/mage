package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChargingWarBoar extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPlaneswalkerPermanent(SubType.DOMRI));
    private static final ConditionHint hint = new ConditionHint(condition, "You control Domri planeswalker");

    public ChargingWarBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // As long as you control a Domri planeswalker, Charging War Boar gets +1/+1 and has trample.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                        condition, "As long as you control a Domri planeswalker, {this} gets +1/+1"
                )
        );
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        TrampleAbility.getInstance(), Duration.WhileOnBattlefield
                ), condition, "and has trample"
        ));
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private ChargingWarBoar(final ChargingWarBoar card) {
        super(card);
    }

    @Override
    public ChargingWarBoar copy() {
        return new ChargingWarBoar(this);
    }
}
