package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

/**
 *
 * @author TheElk801
 */
public final class MoonEatingDog extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent(
                    SubType.YANLING,
                    "a Yanling planeswalker"
            );

    public MoonEatingDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as you control a Yanling planeswalker, Moon-Eating Dog has flying.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                FlyingAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ),
                        new PermanentsOnTheBattlefieldCondition(filter),
                        "As long as you control a Yanling planeswalker, {this} has flying."
                )
        ));
    }

    private MoonEatingDog(final MoonEatingDog card) {
        super(card);
    }

    @Override
    public MoonEatingDog copy() {
        return new MoonEatingDog(this);
    }
}
