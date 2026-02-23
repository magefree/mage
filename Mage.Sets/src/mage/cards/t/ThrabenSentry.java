package mage.cards.t;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class ThrabenSentry extends TransformingDoubleFacedCard {

    public ThrabenSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{3}{W}",
                "Thraben Militia",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "W"
        );

        // Thraben Sentry
        this.getLeftHalfCard().setPT(2, 2);

        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Whenever another creature you control dies, you may transform Thraben Sentry.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(new TransformSourceEffect(), true, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));

        // Thraben Militia
        this.getRightHalfCard().setPT(5, 4);

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
