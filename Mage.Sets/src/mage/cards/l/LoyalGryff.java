package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoyalGryff extends CardImpl {

    public LoyalGryff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HIPPOGRIFF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Loyal Gryff enters the battlefield, you may return another creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ReturnToHandChosenControlledPermanentEffect(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL), true
        ));
    }

    private LoyalGryff(final LoyalGryff card) {
        super(card);
    }

    @Override
    public LoyalGryff copy() {
        return new LoyalGryff(this);
    }
}
