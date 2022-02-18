
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class AetherwindBasker extends CardImpl {

    public AetherwindBasker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Aetherwind Basker enters the battlefield or attacks, you get {E} for each creature you control.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GetEnergyCountersControllerEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED))));

        // Pay {E}: Aetherwind Basker gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new PayEnergyCost(1)));
    }

    private AetherwindBasker(final AetherwindBasker card) {
        super(card);
    }

    @Override
    public AetherwindBasker copy() {
        return new AetherwindBasker(this);
    }
}
