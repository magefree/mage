package mage.cards.g;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GatstafShepherd extends TransformingDoubleFacedCard {

    public GatstafShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{1}{G}",
                "Gatstaf Howler",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Gatstaf Shepherd
        this.getLeftHalfCard().setPT(2, 2);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Gatstaf Shepherd.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Gatstaf Howler
        this.getRightHalfCard().setPT(3, 3);

        // Intimidate
        this.getRightHalfCard().addAbility(IntimidateAbility.getInstance());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Gatstaf Howler.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private GatstafShepherd(final GatstafShepherd card) {
        super(card);
    }

    @Override
    public GatstafShepherd copy() {
        return new GatstafShepherd(this);
    }
}
