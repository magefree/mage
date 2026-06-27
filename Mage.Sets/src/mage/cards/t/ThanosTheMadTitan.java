package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author muz
 */
public final class ThanosTheMadTitan extends CardImpl {

    public ThanosTheMadTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ETERNAL);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch, lifelink
        this.addAbility(DeathtouchAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());

        // Power-up -- {C}{W}{U}{B}{R}{G}: Put two +1/+1 counters on Thanos. Choose odd or even. Destroy each other creature with mana value of the chosen quality.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new ManaCostsImpl<>("{C}{W}{U}{B}{R}{G}")
        );
        ability.addEffect(new ThanosTheMadTitanEffect());
        this.addAbility(ability);
    }

    private ThanosTheMadTitan(final ThanosTheMadTitan card) {
        super(card);
    }

    @Override
    public ThanosTheMadTitan copy() {
        return new ThanosTheMadTitan(this);
    }
}

class ThanosTheMadTitanEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>(Arrays.asList("odd", "even"));

    ThanosTheMadTitanEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Choose odd or even. Destroy each other creature with mana value of the chosen quality";
    }

    private ThanosTheMadTitanEffect(final ThanosTheMadTitanEffect effect) {
        super(effect);
    }

    @Override
    public ThanosTheMadTitanEffect copy() {
        return new ThanosTheMadTitanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent thanos = source.getSourcePermanentIfItStillExists(game);
        if (player == null || thanos == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose odd or even");
        choice.setChoices(choices);
        boolean odd = "odd".equals(choice.getChoice());
        game.getBattlefield()
            .getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)
            .stream()
            .filter(permanent -> !permanent.getId().equals(thanos.getId()))
            .filter(permanent -> odd == ((permanent.getManaValue() & 1) == 1))
            .forEach(permanent -> permanent.destroy(source, game, false));
        return true;
    }
}
