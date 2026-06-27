package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class StolenStarkTech extends CardImpl {

    public StolenStarkTech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this Equipment enters, attach it to target creature you control. That creature gains indestructible until end of turn.
        Ability ability = new EntersBattlefieldAttachToTarget();
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("That creature gains indestructible until end of turn"));
        this.addAbility(ability);

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private StolenStarkTech(final StolenStarkTech card) {
        super(card);
    }

    @Override
    public StolenStarkTech copy() {
        return new StolenStarkTech(this);
    }
}
