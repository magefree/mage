package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class MoldgrafMillipede extends CardImpl {

    public MoldgrafMillipede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Moldgraf Millipede enters the battlefield, mill three cards, then put a +1/+1 counter on Moldgraf Millipede for each creature card in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE), false).concatBy(", then"));
        this.addAbility(ability);
    }

    private MoldgrafMillipede(final MoldgrafMillipede card) {
        super(card);
    }

    @Override
    public MoldgrafMillipede copy() {
        return new MoldgrafMillipede(this);
    }
}
