package mage.cards.m;

import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MithrilCoat extends CardImpl {

    public MithrilCoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // When Mithril Coat enters the battlefield, attach it to target legendary creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY));

        // Equipped creature has indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                IndestructibleAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private MithrilCoat(final MithrilCoat card) {
        super(card);
    }

    @Override
    public MithrilCoat copy() {
        return new MithrilCoat(this);
    }
}
