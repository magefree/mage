package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Grimdancer extends CardImpl {

    public Grimdancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Grimdancer enters the battlefield with your choice of two different counters on it from among menace, deathtouch, and lifelink.
        this.addAbility(new EntersBattlefieldAbility(new GrimdancerEffect()));
    }

    private Grimdancer(final Grimdancer card) {
        super(card);
    }

    @Override
    public Grimdancer copy() {
        return new Grimdancer(this);
    }
}

class GrimdancerEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add("Menace and deathtouch");
        choices.add("Menace and lifelink");
        choices.add("Deathtouch and lifelink");
    }

    GrimdancerEffect() {
        super(Outcome.Benefit);
        staticText = "with your choice of two different counters on it from among menace, deathtouch, and lifelink";
    }

    private GrimdancerEffect(final GrimdancerEffect effect) {
        super(effect);
    }

    @Override
    public GrimdancerEffect copy() {
        return new GrimdancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose two abilities");
        choice.setChoices(choices);
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        Counter counter1 = null;
        Counter counter2 = null;
        switch (choice.getChoice()) {
            case "Menace and deathtouch":
                counter1 = CounterType.MENACE.createInstance();
                counter2 = CounterType.DEATHTOUCH.createInstance();
                break;
            case "Menace and lifelink":
                counter1 = CounterType.MENACE.createInstance();
                counter2 = CounterType.LIFELINK.createInstance();
                break;
            case "Deathtouch and lifelink":
                counter1 = CounterType.DEATHTOUCH.createInstance();
                counter2 = CounterType.LIFELINK.createInstance();
                break;
        }
        permanent.addCounters(counter1, source.getControllerId(), source, game);
        permanent.addCounters(counter2, source.getControllerId(), source, game);
        return true;
    }
}
