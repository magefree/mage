package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.RasputinKnightToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class RasputinTheOneiromancer extends CardImpl {

    public RasputinTheOneiromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When Rasputin, the Oneiromancer enters the battlefield, put a dream counter on it for each
        // opponent you have. Each opponent creates a 1/1 red Goblin creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(
                CounterType.DREAM.createInstance(), OpponentsCount.instance, false
        ).setText("put a dream counter on it for each opponent you have."));
        ability.addEffect(new CreateTokenAllEffect(new GoblinToken(), TargetController.OPPONENT));
        this.addAbility(ability);

        // {T}, Remove one or more dream counters from Rasputin: Add that much {C}.
        Ability ability2 = new DynamicManaAbility(
                Mana.ColorlessMana(1),
                RemovedCountersForCostValue.instance,
                new TapSourceCost(),
                "Add that much {C}",
                true, new CountersSourceCount(CounterType.DREAM));
        ability2.addCost(new RemoveVariableCountersSourceCost(CounterType.DREAM.createInstance(), 1,
                "Remove one or more dream counters from {this}"));
        this.addAbility(ability2);

        // {T}, Remove a dream counter from Rasputin: Create a 2/2 white Knight creature token with protection from red.
        Ability ability3 = new SimpleActivatedAbility(new CreateTokenEffect(new RasputinKnightToken()), new TapSourceCost());
        ability3.addCost(new RemoveCountersSourceCost(CounterType.DREAM.createInstance(1)));
        this.addAbility(ability3);
    }

    private RasputinTheOneiromancer(final RasputinTheOneiromancer card) {
        super(card);
    }

    @Override
    public RasputinTheOneiromancer copy() {
        return new RasputinTheOneiromancer(this);
    }
}
