package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOpponentTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightshadeHarvester extends CardImpl {

    public NightshadeHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a land enters the battlefield under an opponent's control, that player loses 1 life. Put a +1/+1 counter on Nightshade Harvester.
        Ability ability = new EntersBattlefieldOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), StaticFilters.FILTER_LAND_A, false,
                SetTargetPointer.PLAYER
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
    }

    private NightshadeHarvester(final NightshadeHarvester card) {
        super(card);
    }

    @Override
    public NightshadeHarvester copy() {
        return new NightshadeHarvester(this);
    }
}
