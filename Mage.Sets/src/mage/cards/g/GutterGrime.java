package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.GutterGrimeToken;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class GutterGrime extends CardImpl {

    public GutterGrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // Whenever a nontoken creature you control dies, put a slime counter on Gutter Grime, then create a green Ooze creature token with "This creature's power and toughness are each equal to the number of slime counters on Gutter Grime."
        Ability ability = new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.SLIME.createInstance()),
                false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN);
        ability.addEffect(new GutterGrimeEffect());
        this.addAbility(ability);
    }

    private GutterGrime(final GutterGrime card) {
        super(card);
    }

    @Override
    public GutterGrime copy() {
        return new GutterGrime(this);
    }
}

class GutterGrimeEffect extends OneShotEffect {

    GutterGrimeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = ", then create a green Ooze creature token with " +
                "\"This creature's power and toughness are each equal to the number of slime counters on {this}.\"";
    }

    private GutterGrimeEffect(final GutterGrimeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        GutterGrimeToken token = new GutterGrimeToken(source.getSourceId());
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }

    @Override
    public GutterGrimeEffect copy() {
        return new GutterGrimeEffect(this);
    }

}
