
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.SpellsCostIncreasementAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LoneFox

 */
public final class FerozsBan extends CardImpl {

    public FerozsBan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // Creature spells cost {2} more to cast.
        Effect effect = new SpellsCostIncreasementAllEffect(new FilterCreatureCard(), 2);
        effect.setText("Creature spells cost {2} more to cast.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public FerozsBan(final FerozsBan card) {
        super(card);
    }

    @Override
    public FerozsBan copy() {
        return new FerozsBan(this);
    }
}
