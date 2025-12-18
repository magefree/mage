package mage.cards.s;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SolitaryHunter extends TransformingDoubleFacedCard {

    public SolitaryHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARRIOR, SubType.WEREWOLF}, "{3}{G}",
                "One of the Pack",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Solitary Hunter
        this.getLeftHalfCard().setPT(3, 4);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Solitary Hunter.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // One of the Pack
        this.getRightHalfCard().setPT(5, 6);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform One of the Pack.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private SolitaryHunter(final SolitaryHunter card) {
        super(card);
    }

    @Override
    public SolitaryHunter copy() {
        return new SolitaryHunter(this);
    }
}