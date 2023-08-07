
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class GreaterGood extends CardImpl {

    public GreaterGood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Sacrifice a creature: Draw cards equal to the sacrificed creature's power, then discard three cards.
        Effect effect = new DrawCardSourceControllerEffect(SacrificeCostCreaturesPower.instance);
        effect.setText("Draw cards equal to the sacrificed creature's power");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect,
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        effect = new DiscardControllerEffect(3);
        effect.setText(", then discard three cards");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GreaterGood(final GreaterGood card) {
        super(card);
    }

    @Override
    public GreaterGood copy() {
        return new GreaterGood(this);
    }
}
