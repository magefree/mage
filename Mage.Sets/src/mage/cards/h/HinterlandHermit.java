package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class HinterlandHermit extends TransformingDoubleFacedCard {

    public HinterlandHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{1}{R}",
                "Hinterland Scourge",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Hinterland Hermit
        this.getLeftHalfCard().setPT(2, 1);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Hinterland Hermit.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Hinterland Scourge
        this.getRightHalfCard().setPT(3, 2);

        // Hinterland Scourge must be blocked if able.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Hinterland Scourge.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private HinterlandHermit(final HinterlandHermit card) {
        super(card);
    }

    @Override
    public HinterlandHermit copy() {
        return new HinterlandHermit(this);
    }
}
