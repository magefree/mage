package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RescuerChwinga extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public RescuerChwinga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Natural Shelter — When Rescuer Chwinga enters the battlefield, you may return another permanent you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ReturnToHandChosenControlledPermanentEffect(filter), true
        ).withFlavorWord("Natural Shelter"));
    }

    private RescuerChwinga(final RescuerChwinga card) {
        super(card);
    }

    @Override
    public RescuerChwinga copy() {
        return new RescuerChwinga(this);
    }
}
