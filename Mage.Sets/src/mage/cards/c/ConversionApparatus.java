package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConversionApparatus extends CardImpl {

    public ConversionApparatus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}: You get {E}{E}{E}.
        Ability ability = new SimpleActivatedAbility(new GetEnergyCountersControllerEffect(3), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Pay {E}{E}{E}: Add three mana in any combination of colors.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaInAnyCombinationEffect(3), new TapSourceCost());
        ability.addCost(new PayEnergyCost(3));
        this.addAbility(ability);
    }

    private ConversionApparatus(final ConversionApparatus card) {
        super(card);
    }

    @Override
    public ConversionApparatus copy() {
        return new ConversionApparatus(this);
    }
}
