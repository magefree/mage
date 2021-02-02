
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class TivadarsCrusade extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Goblins");
    
    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public TivadarsCrusade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}{W}");

        // Destroy all Goblins.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private TivadarsCrusade(final TivadarsCrusade card) {
        super(card);
    }

    @Override
    public TivadarsCrusade copy() {
        return new TivadarsCrusade(this);
    }
}
