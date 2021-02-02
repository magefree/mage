
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BloodFunnel extends CardImpl {

    private static final FilterCard filter = new FilterCard("Noncreature spells");
    private static final FilterSpell filterNoncreature = new FilterSpell("a noncreature spell");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filterNoncreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public BloodFunnel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Noncreature spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 2)));

        // Whenever you cast a noncreature spell, counter that spell unless you sacrifice a creature.
        Effect effect = new CounterUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        effect.setText("counter that spell unless you sacrifice a creature");
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD,
                effect,
                filterNoncreature,
                false,
                true));
    }

    private BloodFunnel(final BloodFunnel card) {
        super(card);
    }

    @Override
    public BloodFunnel copy() {
        return new BloodFunnel(this);
    }
}
