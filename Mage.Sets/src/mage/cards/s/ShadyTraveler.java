package mage.cards.s;

import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadyTraveler extends TransformingDoubleFacedCard {

    public ShadyTraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{B}",
                "Stalking Predator",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "B"
        );

        // Shady Traveler
        this.getLeftHalfCard().setPT(2, 3);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility());

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Stalking Predator
        this.getRightHalfCard().setPT(4, 4);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private ShadyTraveler(final ShadyTraveler card) {
        super(card);
    }

    @Override
    public ShadyTraveler copy() {
        return new ShadyTraveler(this);
    }
}
