package mage.cards.c;

import java.util.UUID;
import mage.filter.predicate.Predicates;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.Ability;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CetiEel extends CardImpl {

    private static final FilterCard filter1 = new FilterCard("artifact and/or creature card");
    private static final CardsInControllerGraveyardCount xValue = new CardsInControllerGraveyardCount(filter1);
    private static final Hint hint = new ValueHint("Artifact and/or creature cards in your graveyard", xValue);

    static {
        filter1.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate()
        ));
    }

    public CetiEel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.WORM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, mill two cards, then put a +1/+1 counter on this creature for each artifact and/or creature card in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), xValue, false
        ).setText("then put a +1/+1 counter on this creature for each artifact and/or creature card in your graveyard"));

        this.addAbility(ability.addHint(hint));
    }

    private CetiEel(final CetiEel card) {
        super(card);
    }

    @Override
    public CetiEel copy() {
        return new CetiEel(this);
    }
}
