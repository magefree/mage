
package mage.cards.b;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class BrightstoneRitual extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Goblin on the battlefield");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public BrightstoneRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Add {R} for each Goblin on the battlefield.
        this.getSpellAbility().addEffect(new DynamicManaEffect(Mana.RedMana(1), new PermanentsOnBattlefieldCount(filter)));
    }

    private BrightstoneRitual(final BrightstoneRitual card) {
        super(card);
    }

    @Override
    public BrightstoneRitual copy() {
        return new BrightstoneRitual(this);
    }
}
