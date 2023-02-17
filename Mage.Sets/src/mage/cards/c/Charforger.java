package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PhyrexianBeastToxicToken;
import mage.game.permanent.token.PhyrexianGoblinToken;

import java.util.UUID;

public class Charforger extends CardImpl {
    public Charforger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.BEAST);
        this.power = new MageInt(2);
        this. toughness = new MageInt(3);

        //When Charforger enters the battlefield, create a 1/1 red Phyrexian Goblin creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new PhyrexianGoblinToken())
        ));

        //Whenever another creature or artifact you control is put into a graveyard from the battlefield, put an oil counter on Charforger.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()), false,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_OR_CREATURE, false
        ));

        //Remove three oil counters from Charforger: Exile the top card of your library. You may play that card this turn.
        this.addAbility(new SimpleActivatedAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(1)
                .setText("Exile the top card of your library. You may play that card this turn"),
                new RemoveCountersSourceCost(CounterType.OIL.createInstance(3))
        ));
    }

    private Charforger(final Charforger card) {
        super(card);
    }

    @Override
    public Charforger copy() {
        return new Charforger(this);
    }
}
