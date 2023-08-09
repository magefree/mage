
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class Sacrifice extends CardImpl {

    public Sacrifice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // As an additional cost to cast Sacrifice, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        // Add an amount of {B} equal to the sacrificed creature's converted mana cost.
        this.getSpellAbility().addEffect(new DynamicManaEffect(Mana.BlackMana(1), new SacrificeCostConvertedMana("creature"),
                "add an amount of {B} equal to the sacrificed creature's mana value"));
    }

    private Sacrifice(final Sacrifice card) {
        super(card);
    }

    @Override
    public Sacrifice copy() {
        return new Sacrifice(this);
    }
}
