package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CelestialMessenger extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent(SubType.YANLING);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public CelestialMessenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Celestial Messenger gets +1/+1 as long as you control a Yanling planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                condition, "{this} gets +1/+1 as long as you control a Yanling planeswalker"
        )));
    }

    private CelestialMessenger(final CelestialMessenger card) {
        super(card);
    }

    @Override
    public CelestialMessenger copy() {
        return new CelestialMessenger(this);
    }
}
