package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.XiraBlackInsectToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EumidianHatchery extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.HATCHLING);

    public EumidianHatchery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Pay 1 life: Add {B}. Put a hatchling counter on this land.
        Ability ability = new BlackManaAbility();
        ability.addCost(new PayLifeCost(1));
        ability.addEffect(new AddCountersSourceEffect(CounterType.HATCHLING.createInstance()));
        this.addAbility(ability);

        // When this land is put into a graveyard from the battlefield, for each hatchling counter on it, create a 1/1 black Insect creature token with flying.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(
                new CreateTokenEffect(new XiraBlackInsectToken(), xValue)
                        .setText("for each hatchling counter on it, create a 1/1 black Insect creature token with flying")
        ));
    }

    private EumidianHatchery(final EumidianHatchery card) {
        super(card);
    }

    @Override
    public EumidianHatchery copy() {
        return new EumidianHatchery(this);
    }
}
