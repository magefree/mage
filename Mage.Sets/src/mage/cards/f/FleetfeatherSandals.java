package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LevelX2
 */
public final class FleetfeatherSandals extends CardImpl {

    public FleetfeatherSandals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has flying and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT));
        ability.addEffect(new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and haste"));
        this.addAbility(ability);
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private FleetfeatherSandals(final FleetfeatherSandals card) {
        super(card);
    }

    @Override
    public FleetfeatherSandals copy() {
        return new FleetfeatherSandals(this);
    }

}
