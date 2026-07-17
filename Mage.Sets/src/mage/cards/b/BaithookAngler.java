package mage.cards.b;

import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaithookAngler extends TransformingDoubleFacedCard {

    public BaithookAngler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{1}{U}",
                "Hook-Haunt Drifter",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "U"
        );
        this.getLeftHalfCard().setPT(2, 1);
        this.getRightHalfCard().setPT(1, 2);

        // Disturb {1}{U}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{1}{U}"));

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // If Hook-Haunt Drifter would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private BaithookAngler(final BaithookAngler card) {
        super(card);
    }

    @Override
    public BaithookAngler copy() {
        return new BaithookAngler(this);
    }
}
