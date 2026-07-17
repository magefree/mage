package mage.cards.l;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LunarchVeteran extends TransformingDoubleFacedCard {

    public LunarchVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{W}",
                "Luminous Phantom",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.CLERIC}, "W"
        );

        // Lunarch Veteran
        this.getLeftHalfCard().setPT(1, 1);

        // Whenever another creature you control enters, you gain 1 life.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));

        // Disturb {1}{W}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{1}{W}"));

        // Luminous Phantom
        this.getRightHalfCard().setPT(1, 1);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever another creature you control leaves the battlefield, you gain 1 life.
        this.getRightHalfCard().addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));

        // If Luminous Phantom would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private LunarchVeteran(final LunarchVeteran card) {
        super(card);
    }

    @Override
    public LunarchVeteran copy() {
        return new LunarchVeteran(this);
    }
}
