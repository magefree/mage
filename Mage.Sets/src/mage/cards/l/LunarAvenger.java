
package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class LunarAvenger extends CardImpl {

    public LunarAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // Remove a +1/+1 counter from Lunar Avenger: Lunar Avenger gains your choice of flying, first strike, or haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source,
                FlyingAbility.getInstance(), FirstStrikeAbility.getInstance(), HasteAbility.getInstance()),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1))));
    }

    private LunarAvenger(final LunarAvenger card) {
        super(card);
    }

    @Override
    public LunarAvenger copy() {
        return new LunarAvenger(this);
    }
}
