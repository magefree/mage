
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class Fruition extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("for each Forest on the battlefield");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public Fruition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");

        // You gain 1 life for each Forest on the battlefield.
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    private Fruition(final Fruition card) {
        super(card);
    }

    @Override
    public Fruition copy() {
        return new Fruition(this);
    }
}
