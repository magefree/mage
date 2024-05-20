package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifePermanentControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author jimga150
 */
public final class CleopatraExiledPharaoh extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target legendary creatures");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("a legendary creature with counters on it");

    static {
        filter2.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(CounterAnyPredicate.instance);
    }

    public CleopatraExiledPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Allies -- At the beginning of your end step, put a +1/+1 counter on each of up to two other target legendary creatures.
        // Based on Angelic Quartermaster
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false);
        ability.setAbilityWord(AbilityWord.ALLIES);
        ability.addTarget(new TargetPermanent(0, 2, filter));
        this.addAbility(ability);

        // Betrayal -- Whenever a legendary creature with counters on it dies, draw a card for each counter on it. You lose 2 life.
        Ability ability2 = new DiesCreatureTriggeredAbility(new CleopatraExiledPharaohEffect(), false, filter2);
        ability2.setAbilityWord(AbilityWord.BETRAYAL);
        Effect effect = new LoseLifePermanentControllerEffect(2);
        effect.setText("You lose 2 life");
        ability2.addEffect(effect);
        this.addAbility(ability2);
    }

    private CleopatraExiledPharaoh(final CleopatraExiledPharaoh card) {
        super(card);
    }

    @Override
    public CleopatraExiledPharaoh copy() {
        return new CleopatraExiledPharaoh(this);
    }
}

// Based on DrizztDoUrdenEffect
class CleopatraExiledPharaohEffect extends OneShotEffect {

    CleopatraExiledPharaohEffect() {
        super(Outcome.Benefit);
        setText("draw a card for each counter on it");
    }

    private CleopatraExiledPharaohEffect(final CleopatraExiledPharaohEffect effect) {
        super(effect);
    }

    @Override
    public CleopatraExiledPharaohEffect copy() {
        return new CleopatraExiledPharaohEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creatureDied = (Permanent) this.getValue("creatureDied");
        if (creatureDied == null) {
            return false;
        }
        int counterCount = creatureDied
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount).sum();

        Player player = game.getPlayer(source.getControllerId());
        if (player != null
                && player.canRespond()) {
            player.drawCards(counterCount, source, game);
            return true;
        }
        return false;
    }
}
