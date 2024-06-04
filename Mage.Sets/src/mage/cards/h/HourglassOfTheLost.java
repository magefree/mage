package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author grimreap124
 */
public final class HourglassOfTheLost extends CardImpl {

    private static final DynamicValue timeCounter = new CountersSourceCount(CounterType.TIME);

    public HourglassOfTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");
        

        // {T}: Add {W}. Put a time counter on Hourglass of the Lost.
        Ability ability = new WhiteManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.TIME.createInstance()));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {T}, Remove X time counters from Hourglass of the Lost and exile it: Return each nonland permanent card with mana value X from your graveyard to the battlefield. Activate only as a sorcery.
        ability = new Return(timeCounter);
        this.addAbility(new HourglassOfTheLostAbility());
    }

    private HourglassOfTheLost(final HourglassOfTheLost card) {
        super(card);
    }

    @Override
    public HourglassOfTheLost copy() {
        return new HourglassOfTheLost(this);
    }
}
