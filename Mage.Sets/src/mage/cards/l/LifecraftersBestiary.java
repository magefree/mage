
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class LifecraftersBestiary extends CardImpl {

    public LifecraftersBestiary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of your upkeep, scry 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ScryEffect(1, false), TargetController.YOU, false));

        // Whenever you cast a creature spell, you may pay {G}. If you do, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{G}")), StaticFilters.FILTER_SPELL_A_CREATURE, false));
    }

    private LifecraftersBestiary(final LifecraftersBestiary card) {
        super(card);
    }

    @Override
    public LifecraftersBestiary copy() {
        return new LifecraftersBestiary(this);
    }
}
