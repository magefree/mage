
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PreventDamageToAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class GeneralsKabuto extends CardImpl {

    public GeneralsKabuto(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has shroud. (It can't be the target of spells or abilities.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ShroudAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Prevent all combat damage that would be dealt to equipped creature
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT, true)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.PreventDamage, new GenericManaCost(2)));
    }

    private GeneralsKabuto(final GeneralsKabuto card) {
        super(card);
    }

    @Override
    public GeneralsKabuto copy() {
        return new GeneralsKabuto(this);
    }
}
