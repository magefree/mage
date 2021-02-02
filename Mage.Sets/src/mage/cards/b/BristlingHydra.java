
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class BristlingHydra extends CardImpl {

    public BristlingHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Bristling Hydra enters the battlefield, you get {E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3)));

        // Pay {E}{E}{E}: Put a +1/+1 count on Bristling Hydra. It gains hexproof until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new PayEnergyCost(3));
        Effect effect = new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("It gains hexproof until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private BristlingHydra(final BristlingHydra card) {
        super(card);
    }

    @Override
    public BristlingHydra copy() {
        return new BristlingHydra(this);
    }
}
