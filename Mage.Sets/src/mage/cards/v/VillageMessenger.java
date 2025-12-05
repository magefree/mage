package mage.cards.v;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class VillageMessenger extends TransformingDoubleFacedCard {

    public VillageMessenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{R}",
                "Moonrise Intruder",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Village Messenger
        this.getLeftHalfCard().setPT(1, 1);

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Village Messenger.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Moonrise Intruder
        this.getRightHalfCard().setPT(2, 2);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Moonrise Intruder.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private VillageMessenger(final VillageMessenger card) {
        super(card);
    }

    @Override
    public VillageMessenger copy() {
        return new VillageMessenger(this);
    }
}