package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.OneOrMoreMilledTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedMilledValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieMutantToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ScreechingScorchbeast extends CardImpl {

    public ScreechingScorchbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Screeching Scorchbeast attacks, each player gets two rad counters.
        this.addAbility(new AttacksTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.RAD.createInstance(2), TargetController.EACH_PLAYER)
        ));

        // Whenever one or more nonland cards are milled, you may create that many 2/2 black Zombie Mutant creature tokens. Do this only once each turn.
        this.addAbility(new OneOrMoreMilledTriggeredAbility(
                StaticFilters.FILTER_CARDS_NON_LAND,
                new CreateTokenEffect(new ZombieMutantToken(), SavedMilledValue.MANY),
                true
        ).setDoOnlyOnceEachTurn(true));
    }

    private ScreechingScorchbeast(final ScreechingScorchbeast card) {
        super(card);
    }

    @Override
    public ScreechingScorchbeast copy() {
        return new ScreechingScorchbeast(this);
    }
}
