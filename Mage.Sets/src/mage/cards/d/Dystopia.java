
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author emerald000
 */
public final class Dystopia extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("green or white permanent");
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN), new ColorPredicate(ObjectColor.WHITE)));
    }

    public Dystopia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");

        // Cumulative upkeep-Pay 1 life.
        this.addAbility(new CumulativeUpkeepAbility(new PayLifeCost(1)));
        
        // At the beginning of each player's upkeep, that player sacrifices a green or white permanent.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeEffect(filter, 1, "that player"), TargetController.ANY, false));
    }

    private Dystopia(final Dystopia card) {
        super(card);
    }

    @Override
    public Dystopia copy() {
        return new Dystopia(this);
    }
}
