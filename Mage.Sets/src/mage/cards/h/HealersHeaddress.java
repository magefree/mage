
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class HealersHeaddress extends CardImpl {

    public HealersHeaddress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +0/+2 and has "{tap}: Prevent the next 1 damage that would be dealt to any target this turn."
        Ability gainAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new TapSourceCost());
        gainAbility.addTarget(new TargetAnyTarget());
        Effect effect = new BoostEquippedEffect(0, 2);
        effect.setText("Equipped creature gets +0/+2");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(gainAbility, AttachmentType.EQUIPMENT);
        effect.setText("and has \"{T}: Prevent the next 1 damage that would be dealt to any target this turn.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
        // {W}{W}: Attach Healer's Headdress to target creature you control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature, "Attach {this} to target creature you control"), new ManaCostsImpl<>("{W}{W}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    private HealersHeaddress(final HealersHeaddress card) {
        super(card);
    }

    @Override
    public HealersHeaddress copy() {
        return new HealersHeaddress(this);
    }
}
