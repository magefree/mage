
package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Topher
 */
public final class BurntOffering extends CardImpl {

    public BurntOffering(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        //As an additional cost to cast Burnt Offering, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        //Add an amount of {B} and/or {R} equal to the sacrificed creature's converted mana cost.
        SacrificeCostConvertedMana xValue = new SacrificeCostConvertedMana("creature");
        this.getSpellAbility().addEffect(new AddManaInAnyCombinationEffect(
                xValue, xValue, ColoredManaSymbol.B, ColoredManaSymbol.R
        ));
    }

    private BurntOffering(final BurntOffering card) {
        super(card);
    }

    @Override
    public BurntOffering copy() {
        return new BurntOffering(this);
    }
}
