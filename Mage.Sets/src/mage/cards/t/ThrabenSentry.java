package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class ThrabenSentry extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ThrabenSentry(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{3}{W}",
                "Thraben Militia",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "W"
        );
        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(5, 4);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Whenever another creature you control dies, you may transform Thraben Sentry.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(
                new TransformSourceEffect(), true, filter
        ));

        // Thraben Militia
        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());
    }

    private ThrabenSentry(final ThrabenSentry card) {
        super(card);
    }

    @Override
    public ThrabenSentry copy() {
        return new ThrabenSentry(this);
    }
}
