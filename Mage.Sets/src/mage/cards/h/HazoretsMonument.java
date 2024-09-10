
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class HazoretsMonument extends CardImpl {

    private static final FilterCard filter = new FilterCard("Red creature spells");
    private static final FilterSpell filter2 = new FilterSpell("a creature spell");

    static {
        filter.add(Predicates.and(new ColorPredicate(ObjectColor.RED), CardType.CREATURE.getPredicate()));
    }
    static {
        filter2.add(CardType.CREATURE.getPredicate());
    }

    public HazoretsMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // Red creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast a creature spell, you may discard a card. If you do, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), filter2, false));
    }

    private HazoretsMonument(final HazoretsMonument card) {
        super(card);
    }

    @Override
    public HazoretsMonument copy() {
        return new HazoretsMonument(this);
    }
}
