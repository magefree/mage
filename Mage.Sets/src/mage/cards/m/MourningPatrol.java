package mage.cards.m;

import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MourningPatrol extends TransformingDoubleFacedCard {

    public MourningPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{2}{W}",
                "Morning Apparition",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.SOLDIER}, "W"
        );

        // Mourning Patrol
        this.getLeftHalfCard().setPT(2, 3);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Disturb {3}{W}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{3}{W}"));

        // Morning Apparition
        this.getRightHalfCard().setPT(2, 1);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // If Morning Apparition would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private MourningPatrol(final MourningPatrol card) {
        super(card);
    }

    @Override
    public MourningPatrol copy() {
        return new MourningPatrol(this);
    }
}
