
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class GravitySphere extends CardImpl {

    static final private FilterPermanent filter = new FilterPermanent("All creatures");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public GravitySphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");
        addSuperType(SuperType.WORLD);

        // All creatures lose flying.
        Effect effect = new LoseAbilityAllEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("All creatures lose flying");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private GravitySphere(final GravitySphere card) {
        super(card);
    }

    @Override
    public GravitySphere copy() {
        return new GravitySphere(this);
    }
}
