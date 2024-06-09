
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class BontusMonument extends CardImpl {

    private static final FilterCard filter = new FilterCard("black creature spells");

    static {
        filter.add(Predicates.and(new ColorPredicate(ObjectColor.BLACK), CardType.CREATURE.getPredicate()));
    }

    public BontusMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // Black creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast a creature spell, each opponent loses 1 life and you gain 1 life.
        Ability ability = new SpellCastControllerTriggeredAbility(new LoseLifeOpponentsEffect(1), StaticFilters.FILTER_SPELL_A_CREATURE, false);
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private BontusMonument(final BontusMonument card) {
        super(card);
    }

    @Override
    public BontusMonument copy() {
        return new BontusMonument(this);
    }
}
