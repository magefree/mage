
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author BetaSteward
 */
public final class HeartlessSummoning extends CardImpl {

    public HeartlessSummoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");


        // Creature spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(new FilterCreatureCard("Creature spells"), 2)));

        // Creatures you control get -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(-1, -1, Duration.WhileOnBattlefield)));
    }

    private HeartlessSummoning(final HeartlessSummoning card) {
        super(card);
    }

    @Override
    public HeartlessSummoning copy() {
        return new HeartlessSummoning(this);
    }
}
