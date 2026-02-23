package mage.cards.h;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HinterlandLogger extends TransformingDoubleFacedCard {

    public HinterlandLogger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{1}{G}",
                "Timber Shredder",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G");

        this.getLeftHalfCard().setPT(2, 1);
        this.getRightHalfCard().setPT(4, 2);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Hinterland Logger.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Timber Shredder

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Timber Shredder.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());


    }

    private HinterlandLogger(final HinterlandLogger card) {
        super(card);
    }

    @Override
    public HinterlandLogger copy() {
        return new HinterlandLogger(this);
    }
}
