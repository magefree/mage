package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TempestHarvester extends CardImpl {

    public TempestHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Tempest Harvester enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // {T}, Pay {E}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new TapSourceCost()
        );
        ability.addCost(new PayEnergyCost(1));
        this.addAbility(ability);
    }

    private TempestHarvester(final TempestHarvester card) {
        super(card);
    }

    @Override
    public TempestHarvester copy() {
        return new TempestHarvester(this);
    }
}
