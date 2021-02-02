
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.UntapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class Awakening extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures and lands");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public Awakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // At the beginning of each upkeep, untap all creatures and lands.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new UntapAllEffect(filter), TargetController.ANY, false));
    }

    private Awakening(final Awakening card) {
        super(card);
    }

    @Override
    public Awakening copy() {
        return new Awakening(this);
    }
}
