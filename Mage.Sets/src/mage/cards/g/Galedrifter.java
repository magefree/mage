package mage.cards.g;

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
public final class Galedrifter extends TransformingDoubleFacedCard {

    public Galedrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HIPPOGRIFF}, "{3}{U}",
                "Waildrifter",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HIPPOGRIFF, SubType.SPIRIT}, "U");

        this.getLeftHalfCard().setPT(3, 2);
        this.getRightHalfCard().setPT(2, 2);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Disturb {4}{U}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{4}{U}"));

        // Waildrifter
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // If Waildrifter would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private Galedrifter(final Galedrifter card) {
        super(card);
    }

    @Override
    public Galedrifter copy() {
        return new Galedrifter(this);
    }
}
