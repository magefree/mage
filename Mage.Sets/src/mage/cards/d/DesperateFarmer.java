package mage.cards.d;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesperateFarmer extends TransformingDoubleFacedCard {

    public DesperateFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{2}{B}",
                "Depraved Harvester",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "B");

        // Desperate Farmer
        this.getLeftHalfCard().setPT(2, 2);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // When another creature you control dies, transform Desperate Farmer.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(
                new TransformSourceEffect(), false, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ).setTriggerPhrase("When another creature you control dies, "));

        // Depraved Harvester
        this.getRightHalfCard().setPT(4, 3);

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());
    }

    private DesperateFarmer(final DesperateFarmer card) {
        super(card);
    }

    @Override
    public DesperateFarmer copy() {
        return new DesperateFarmer(this);
    }
}
