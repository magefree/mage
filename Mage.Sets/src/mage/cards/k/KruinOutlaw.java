package mage.cards.k;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class KruinOutlaw extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent(SubType.WEREWOLF, "Werewolves");

    public KruinOutlaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE, SubType.WEREWOLF}, "{1}{R}{R}",
                "Terror of Kruin Pass",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R");

        // Kruin Outlaw
        this.getLeftHalfCard().setPT(2, 2);

        // First strike
        this.getLeftHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Kruin Outlaw.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Terror of Kruin Pass
        this.getRightHalfCard().setPT(3, 3);

        // Double strike
        this.getRightHalfCard().addAbility(DoubleStrikeAbility.getInstance());

        // Werewolves you control have menace.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.WhileOnBattlefield, filter, false
        )));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Terror of Kruin Pass.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private KruinOutlaw(final KruinOutlaw card) {
        super(card);
    }

    @Override
    public KruinOutlaw copy() {
        return new KruinOutlaw(this);
    }
}
