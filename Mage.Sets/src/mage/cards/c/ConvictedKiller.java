package mage.cards.c;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ConvictedKiller extends TransformingDoubleFacedCard {

    public ConvictedKiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{R}",
                "Branded Howler",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R");

        // Convicted Killer
        this.getLeftHalfCard().setPT(2, 2);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Convicted Killer.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Branded Howler
        this.getRightHalfCard().setPT(4, 4);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Branded Howler.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private ConvictedKiller(final ConvictedKiller card) { super(card); }
    @Override public ConvictedKiller copy() { return new ConvictedKiller(this); }
}
