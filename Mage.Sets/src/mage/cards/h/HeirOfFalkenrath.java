package mage.cards.h;

import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class HeirOfFalkenrath extends TransformingDoubleFacedCard {

    public HeirOfFalkenrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "{1}{B}",
                "Heir to the Night",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.BERSERKER}, "B"
        );

        // Heir of Falkenrath
        this.getLeftHalfCard().setPT(2, 1);

        // Discard a card: Transform Heir of Falkenrath. Activate this ability only once each turn.
        this.getLeftHalfCard().addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new DiscardCardCost()));

        // Heir to the Night
        this.getRightHalfCard().setPT(3, 2);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
    }

    private HeirOfFalkenrath(final HeirOfFalkenrath card) {
        super(card);
    }

    @Override
    public HeirOfFalkenrath copy() {
        return new HeirOfFalkenrath(this);
    }
}
