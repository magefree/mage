
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class Mortarpod extends CardImpl {

    public Mortarpod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon (When this Equipment enters the battlefield, create a 0/0 black Germ creature token, then attach this to it.)
        this.addAbility(new LivingWeaponAbility());
        
        // Equipped creature gets +0/+1 and has "Sacrifice this creature: This creature deals 1 damage to any target."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(0, 1));
        SimpleActivatedAbility abilityToGain = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(1),
                new SacrificeSourceCost());
        abilityToGain.addTarget(new TargetAnyTarget());
        Effect effect = new GainAbilityAttachedEffect(abilityToGain, AttachmentType.EQUIPMENT);
        effect.setText("and has \"Sacrifice this creature: This creature deals 1 damage to any target.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private Mortarpod(final Mortarpod card) {
        super(card);
    }

    @Override
    public Mortarpod copy() {
        return new Mortarpod(this);
    }
}
