package mage.cards.g;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.TransformTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GeierReachBandit extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.WEREWOLF, "a Werewolf");

    public GeierReachBandit(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE, SubType.WEREWOLF}, "{2}{R}",
                "Vildin-Pack Alpha",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );
        this.getLeftHalfCard().setPT(3, 2);
        this.getRightHalfCard().setPT(4, 3);

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Geier Reach Bandit.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Vildin-Pack Alpha
        // Whenever a Werewolf enters the battlefield under your control, you may transform it.
        this.getRightHalfCard().addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new TransformTargetEffect().setText("transform it"),
                filter, true, SetTargetPointer.PERMANENT, null
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Vildin-Pack Alpha.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private GeierReachBandit(final GeierReachBandit card) {
        super(card);
    }

    @Override
    public GeierReachBandit copy() {
        return new GeierReachBandit(this);
    }
}
