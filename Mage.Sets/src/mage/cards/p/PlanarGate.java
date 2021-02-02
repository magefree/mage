
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LevelX2
 */
public final class PlanarGate extends CardImpl {

    public PlanarGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // Creature spells you cast cost up to {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(new FilterCreatureCard("Creature spells"), 2, true)));
    }

    private PlanarGate(final PlanarGate card) {
        super(card);
    }

    @Override
    public PlanarGate copy() {
        return new PlanarGate(this);
    }
}
