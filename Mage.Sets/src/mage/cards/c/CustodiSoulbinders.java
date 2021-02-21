package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class CustodiSoulbinders extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CustodiSoulbinders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Custodi Soulbinders enters the battlefield with X +1/+1 counters on it, where X is the number of other creatures on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(),
                        new PermanentsOnBattlefieldCount(filter),
                        false),
                "with X +1/+1 counters on it, where X is the number of other creatures on the battlefield"));


        // {2}{W}, Remove a +1/+1 counter from Custodi Soulbinders: Create a 1/1 white Spirit creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SpiritWhiteToken()), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
    }

    private CustodiSoulbinders(final CustodiSoulbinders card) {
        super(card);
    }

    @Override
    public CustodiSoulbinders copy() {
        return new CustodiSoulbinders(this);
    }
}
