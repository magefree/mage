
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author Loki
 */
public final class Bonesplitter extends CardImpl {

    public Bonesplitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);
        
        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0)));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), false));
    }

    private Bonesplitter(final Bonesplitter card) {
        super(card);
    }

    @Override
    public Bonesplitter copy() {
        return new Bonesplitter(this);
    }
}
