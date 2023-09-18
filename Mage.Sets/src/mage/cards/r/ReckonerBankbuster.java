package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.PilotToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReckonerBankbuster extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.CHARGE, 0, 0);

    public ReckonerBankbuster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reckoner Bankbuster enters the battlefield with three charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.CHARGE.createInstance(3)
        ), "with three charge counters on it"));

        // {2}, {T}, Remove a charge counter from Reckoner Bankbuster: Draw a card. Then if there are no charge counters on Reckoner Bankbuster, create a Treasure token and a 1/1 colorless Pilot creature token with "This creature crews Vehicles as though its power were 2 greater."
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2)
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new TreasureToken()), condition,
                "Then if there are no charge counters on {this}, create a Treasure token"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new PilotToken()), condition, "and a 1/1 colorless Pilot creature token " +
                "with \"This creature crews Vehicles as though its power were 2 greater.\""
        ));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        this.addAbility(ability);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private ReckonerBankbuster(final ReckonerBankbuster card) {
        super(card);
    }

    @Override
    public ReckonerBankbuster copy() {
        return new ReckonerBankbuster(this);
    }
}
