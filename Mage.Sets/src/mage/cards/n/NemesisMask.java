
package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.MustBeBlockedByAllAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class NemesisMask extends CardImpl {

    public NemesisMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // All creatures able to block equipped creature do so.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new MustBeBlockedByAllAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT)));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    private NemesisMask(final NemesisMask card) {
        super(card);
    }

    @Override
    public NemesisMask copy() {
        return new NemesisMask(this);
    }
}
