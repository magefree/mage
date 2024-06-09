package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.*;

public final class CragflameToken extends TokenImpl {

    public CragflameToken() {
        super("Cragflame", "Cragflame, a legendary colorless Equipment artifact token with \"Equipped creature gets +1/+1 and has vigilance, trample, and haste\" and equip {2}");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.EQUIPMENT);

        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield
        ).setText("and has vigilance"));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield
        ).setText(", trample"));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield
        ).setText(", and haste"));
        this.addAbility(ability);

        this.addAbility(new EquipAbility(2));
    }

    private CragflameToken(final CragflameToken token) {
        super(token);
    }

    public CragflameToken copy() {
        return new CragflameToken(this);
    }
}
