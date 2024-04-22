

package mage.cards.k;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
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
 * @author Loki
 */
public final class KiteShield extends CardImpl {

    public KiteShield (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");
        this.subtype.add(SubType.EQUIPMENT);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(0, 3)));
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));

    }

    private KiteShield(final KiteShield card) {
        super(card);
    }

    @Override
    public KiteShield copy() {
        return new KiteShield(this);
    }

}
