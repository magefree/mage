package mage.cards.h;

import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarvesttideInfiltrator extends TransformingDoubleFacedCard {

    public HarvesttideInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{R}",
                "Harvesttide Assailant",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Harvesttide Infiltrator
        this.getLeftHalfCard().setPT(3, 2);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Harvesttide Assailant
        this.getRightHalfCard().setPT(4, 4);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private HarvesttideInfiltrator(final HarvesttideInfiltrator card) {
        super(card);
    }

    @Override
    public HarvesttideInfiltrator copy() {
        return new HarvesttideInfiltrator(this);
    }
}
