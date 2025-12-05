package mage.cards.t;

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
public final class TormentedPariah extends TransformingDoubleFacedCard {

    public TormentedPariah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARRIOR, SubType.WEREWOLF}, "{3}{R}",
                "Rampaging Werewolf",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Tormented Pariah
        this.getLeftHalfCard().setPT(3, 2);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Tormented Pariah.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Rampaging Werewolf
        this.getRightHalfCard().setPT(6, 4);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Rampaging Werewolf.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private TormentedPariah(final TormentedPariah card) {
        super(card);
    }

    @Override
    public TormentedPariah copy() {
        return new TormentedPariah(this);
    }
}
