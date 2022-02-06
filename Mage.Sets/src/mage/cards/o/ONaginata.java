
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttachableToRestrictedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ONaginata extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with 3 or more power ");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public ONaginata(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        Target target = new TargetControlledCreaturePermanent(1, 1, filter, false);
        // O-Naginata can be attached only to a creature with 3 or more power.
        this.addAbility(new AttachableToRestrictedAbility(target));

        // Equipped creature gets +3/+0 and has trample.
        Effect effect = new BoostEquippedEffect(3, 0);
        effect.setText("Equipped creature gets +3/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and has trample");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), target));
    }

    private ONaginata(final ONaginata card) {
        super(card);
    }

    @Override
    public ONaginata copy() {
        return new ONaginata(this);
    }
}
