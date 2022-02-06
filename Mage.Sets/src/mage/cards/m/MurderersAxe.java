
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MurderersAxe extends CardImpl {

    public MurderersAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));

        // Equip &mdash; Discard a card.
        this.addAbility(new EquipAbility(Outcome.AddAbility, new DiscardCardCost(), false));
    }

    private MurderersAxe(final MurderersAxe card) {
        super(card);
    }

    @Override
    public MurderersAxe copy() {
        return new MurderersAxe(this);
    }
}
