package mage.cards.r;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class RecklessWaif extends TransformingDoubleFacedCard {

    public RecklessWaif(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE, SubType.WEREWOLF}, "{R}",
                "Merciless Predator",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Reckless Waif
        this.getLeftHalfCard().setPT(1, 1);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Reckless Waif.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Merciless Predator
        this.getRightHalfCard().setPT(3, 2);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Merciless Predator.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private RecklessWaif(final RecklessWaif card) {
        super(card);
    }

    @Override
    public RecklessWaif copy() {
        return new RecklessWaif(this);
    }
}
