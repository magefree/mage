
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PreventDamageToAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ShieldOfTheRealm extends CardImpl {

    public ShieldOfTheRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // If a source would deal damage to equipped creature, prevent 2 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new PreventDamageToAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT, 2, false)));
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.PreventDamage, new GenericManaCost(1), false));
    }

    private ShieldOfTheRealm(final ShieldOfTheRealm card) {
        super(card);
    }

    @Override
    public ShieldOfTheRealm copy() {
        return new ShieldOfTheRealm(this);
    }
}
