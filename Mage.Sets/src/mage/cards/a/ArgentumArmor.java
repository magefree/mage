
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class ArgentumArmor extends CardImpl {

    public ArgentumArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +6/+6.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(6, 6)));

        // Whenever equipped creature attacks, destroy target permanent.
        Ability ability = new AttacksAttachedTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // Equip {6}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(6), false));
    }

    private ArgentumArmor(final ArgentumArmor card) {
        super(card);
    }

    @Override
    public ArgentumArmor copy() {
        return new ArgentumArmor(this);
    }
}
