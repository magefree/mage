package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Skateboard extends CardImpl {

    public Skateboard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, tap target permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // Equipped creature gets +1/+0 and has haste.
        Ability ability2 = new SimpleStaticAbility(new BoostEnchantedEffect(1, 0));
        ability2.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText(" and has haste"));
        this.addAbility(ability2);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private Skateboard(final Skateboard card) {
        super(card);
    }

    @Override
    public Skateboard copy() {
        return new Skateboard(this);
    }
}
