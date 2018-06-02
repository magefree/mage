
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.SpellsCostIncreasementAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author fireshoes
 */
public final class Squeeze extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Sorcery spells");
    
    static {
        filter.add(new CardTypePredicate(CardType.SORCERY));
    }

    public Squeeze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");

        // Sorcery spells cost {3} more to cast.
        Effect effect = new SpellsCostIncreasementAllEffect(filter, 3);
        effect.setText("Sorcery spells cost {3} more to cast");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public Squeeze(final Squeeze card) {
        super(card);
    }

    @Override
    public Squeeze copy() {
        return new Squeeze(this);
    }
}
