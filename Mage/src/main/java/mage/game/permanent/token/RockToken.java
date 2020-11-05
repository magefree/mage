package mage.game.permanent.token;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeAttachmentCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

public final class RockToken extends TokenImpl {

    public RockToken() {
        super("Rock", "artifact equipment token named Rock with \"Equipped creature has '{1}, {T}, Sacrifice Rock: This creature deals 2 damage to any target'\" and equip {1}");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.EQUIPMENT);

        this.addAbility(new SimpleStaticAbility(new GainAbilityWithAttachmentEffect(
                "equipped creature has \"{1}, {T}, Sacrifice {this}: This creature deals 2 damage to any target.\"",
                new DamageTargetEffect(2), new TargetAnyTarget(), new SacrificeAttachmentCost(), new GenericManaCost(1), new TapSourceCost()
        )));
        this.addAbility(new EquipAbility(1));
    }

    public RockToken(final RockToken token) {
        super(token);
    }

    public RockToken copy() {
        return new RockToken(this);
    }
}
