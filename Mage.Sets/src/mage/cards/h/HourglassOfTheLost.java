package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

public final class HourglassOfTheLost extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("nonland permanent card from your graveyard");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(HourglassOfTheLostPredicate.instance);
    }

    public HourglassOfTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // {T}: Add {W}. Put a time counter on Hourglass of the Lost.
        final Ability ability = new WhiteManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.TIME.createInstance()));
        this.addAbility(ability);

        // {T}, Remove X time counters from this artifact and exile it: Return each nonland permanent card with mana value X from your graveyard to the battlefield. Activate only as a sorcery.
        final Ability ability2 = new ActivateAsSorceryActivatedAbility(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter)
            .setText("Return each nonland permanent card with mana value X from your graveyard to the battlefield"), new TapSourceCost());
        ability2.addCost(new RemoveVariableCountersSourceCost(CounterType.TIME));
        ability2.addCost(new ExileSourceCost().setText("exile it"));
        this.addAbility(ability2);
    }

    private HourglassOfTheLost(final HourglassOfTheLost card) {
        super(card);
    }

    @Override
    public HourglassOfTheLost copy() {
        return new HourglassOfTheLost(this);
    }
}

enum HourglassOfTheLostPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() == GetXValue.instance.calculate(game, input.getSource(), null);
    }
}
