
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox

 */
public final class ThrowingKnife extends CardImpl {

    public ThrowingKnife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0)));
        // Whenever equipped creature attacks, you may sacrifice Throwing Knife. If you do, Throwing Knife deals 2 damage to any target.
        Effect effect = new SacrificeSourceEffect();
        effect.setText("you may sacrifice {this}. If you do, ");
        Ability ability = new AttacksAttachedTriggeredAbility(new SacrificeSourceEffect(), true);
        ability.addEffect(new DamageTargetEffect(2));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private ThrowingKnife(final ThrowingKnife card) {
        super(card);
    }

    @Override
    public ThrowingKnife copy() {
        return new ThrowingKnife(this);
    }
}
