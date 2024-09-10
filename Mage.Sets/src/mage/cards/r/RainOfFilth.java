
package mage.cards.r;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class RainOfFilth extends CardImpl {

    public RainOfFilth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Until end of turn, lands you control gain "Sacrifice this land: Add {B}."
        ActivatedManaAbilityImpl ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new SacrificeSourceCost().setText("sacrifice this land"));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(ability, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS)
                .setText("until end of turn, lands you control gain \"Sacrifice this land: Add {B}.\""));
    }

    private RainOfFilth(final RainOfFilth card) {
        super(card);
    }

    @Override
    public RainOfFilth copy() {
        return new RainOfFilth(this);
    }
}
