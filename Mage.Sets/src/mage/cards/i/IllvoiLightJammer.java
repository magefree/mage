package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class IllvoiLightJammer extends CardImpl {

    public IllvoiLightJammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this Equipment enters, attach it to target creature you control. That creature gains hexproof until end of turn.
        Ability ability = new EntersBattlefieldAttachToTarget();
        ability.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("That creature gains hexproof until end of turn"));
        this.addAbility(ability);

        // Equipped creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 2)));

        // Equip {1}
        this.addAbility(new EquipAbility(3));
    }

    private IllvoiLightJammer(final IllvoiLightJammer card) {
        super(card);
    }

    @Override
    public IllvoiLightJammer copy() {
        return new IllvoiLightJammer(this);
    }
}
