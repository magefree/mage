package mage.cards.g;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GrizzledOutcasts extends TransformingDoubleFacedCard {

    public GrizzledOutcasts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{4}{G}",
                "Krallenhorde Wantons",
                new SuperType[]{}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Grizzled Outcasts
        this.getLeftHalfCard().setPT(4, 4);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Grizzled Outcasts.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Krallenhorde Wantons
        this.getRightHalfCard().setPT(7, 7);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Krallenhorde Wantons.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private GrizzledOutcasts(final GrizzledOutcasts card) {
        super(card);
    }

    @Override
    public GrizzledOutcasts copy() {
        return new GrizzledOutcasts(this);
    }
}