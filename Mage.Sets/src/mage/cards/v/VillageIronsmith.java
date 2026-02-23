package mage.cards.v;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class VillageIronsmith extends TransformingDoubleFacedCard {

    public VillageIronsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{1}{R}",
                "Ironfang",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Village Ironsmith
        this.getLeftHalfCard().setPT(1, 1);

        this.getLeftHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Village Ironsmith.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Ironfang
        this.getRightHalfCard().setPT(3, 1);

        this.getRightHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ironfang.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private VillageIronsmith(final VillageIronsmith card) {
        super(card);
    }

    @Override
    public VillageIronsmith copy() {
        return new VillageIronsmith(this);
    }
}
