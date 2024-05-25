package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RoilCartographer extends CardImpl {

    public RoilCartographer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land enters the battlefield under your control, you get {E}.
        this.addAbility(new LandfallAbility(
                new GetEnergyCountersControllerEffect(1), false
        ));

        // {T}, Pay six {E}: Draw three cards.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(3),
                new TapSourceCost()
        );
        ability.addCost(new PayEnergyCost(6));
        this.addAbility(ability);
    }

    private RoilCartographer(final RoilCartographer card) {
        super(card);
    }

    @Override
    public RoilCartographer copy() {
        return new RoilCartographer(this);
    }
}
