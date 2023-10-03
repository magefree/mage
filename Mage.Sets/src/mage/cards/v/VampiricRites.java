
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VampiricRites extends CardImpl {

    public VampiricRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // {1}{B}, Sacrifice a creature: You gain 1 life and draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("and draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private VampiricRites(final VampiricRites card) {
        super(card);
    }

    @Override
    public VampiricRites copy() {
        return new VampiricRites(this);
    }
}
