
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ShieldedAetherThief extends CardImpl {

    public ShieldedAetherThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever Shield Aether Thief blocks, you get {E}.
        this.addAbility(new BlocksSourceTriggeredAbility(new GetEnergyCountersControllerEffect(1), false));

        // {T}, Pay {E}{E}{E}: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new PayEnergyCost(3));
        this.addAbility(ability);
    }

    private ShieldedAetherThief(final ShieldedAetherThief card) {
        super(card);
    }

    @Override
    public ShieldedAetherThief copy() {
        return new ShieldedAetherThief(this);
    }
}
