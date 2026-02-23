package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IdolOfTheDeepKing extends TransformingDoubleFacedCard {

    public IdolOfTheDeepKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}{R}",
                "Sovereign's Macuahuitl",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "R"
        );

        // Idol of the Deep King
        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // When Idol of the Deep King enters the battlefield, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2));
        ability.addTarget(new TargetAnyTarget());
        this.getLeftHalfCard().addAbility(ability);

        // Craft with artifact {2}{R}
        this.getLeftHalfCard().addAbility(new CraftAbility("{2}{R}"));

        // Sovereign's Macuahuitl
        // When Sovereign's Macuahuitl enters the battlefield, attach it to target creature you control.
        this.getRightHalfCard().addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +2/+0.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Equip {2}
        this.getRightHalfCard().addAbility(new EquipAbility(2));
    }

    private IdolOfTheDeepKing(final IdolOfTheDeepKing card) {
        super(card);
    }

    @Override
    public IdolOfTheDeepKing copy() {
        return new IdolOfTheDeepKing(this);
    }
}
