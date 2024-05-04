package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverShroudCostume extends CardImpl {

    public SilverShroudCostume(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Silver Shroud Costume enters the battlefield, attach it to target creature you control. That creature gains shroud until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach it to target creature you control"
        ));
        ability.addEffect(new GainAbilityTargetEffect(
                ShroudAbility.getInstance(), Duration.EndOfTurn
        ).setText("That creature gains shroud until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Equipped creature can't be blocked.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT)));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private SilverShroudCostume(final SilverShroudCostume card) {
        super(card);
    }

    @Override
    public SilverShroudCostume copy() {
        return new SilverShroudCostume(this);
    }
}
