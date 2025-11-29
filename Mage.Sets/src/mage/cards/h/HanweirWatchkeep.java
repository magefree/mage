package mage.cards.h;

import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class HanweirWatchkeep extends TransformingDoubleFacedCard {

    public HanweirWatchkeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARRIOR, SubType.WEREWOLF}, "{2}{R}",
                "Bane of Hanweir",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Hanweir Watchkeep
        this.getLeftHalfCard().setPT(1, 5);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Hanweir Watchkeep.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Bane of Hanweir
        this.getRightHalfCard().setPT(5, 5);

        // Bane of Hanweir attacks each turn if able.
        this.getRightHalfCard().addAbility(new AttacksEachCombatStaticAbility());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Bane of Hanweir.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private HanweirWatchkeep(final HanweirWatchkeep card) {
        super(card);
    }

    @Override
    public HanweirWatchkeep copy() {
        return new HanweirWatchkeep(this);
    }
}
