package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GnomeToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThreefoldThunderhulk extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public ThreefoldThunderhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Threefold Thunderhulk enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                "with three +1/+1 counters on it"
        ));

        // Whenever Threefold Thunderhulk enters the battlefield or attacks, create a number of 1/1 colorless Gnome artifact creature tokens equal to its power.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new CreateTokenEffect(new GnomeToken(), xValue)
                        .setText("create a number of 1/1 colorless Gnome artifact creature tokens equal to its power")
        ));


        // {2}, Sacrifice another artifact: Put a +1/+1 counter on Threefold Thunderhulk.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new GenericManaCost(2)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_SHORT_TEXT));
        this.addAbility(ability);
    }

    private ThreefoldThunderhulk(final ThreefoldThunderhulk card) {
        super(card);
    }

    @Override
    public ThreefoldThunderhulk copy() {
        return new ThreefoldThunderhulk(this);
    }
}
