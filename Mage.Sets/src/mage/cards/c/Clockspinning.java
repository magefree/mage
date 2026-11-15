package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrSuspendedCard;
import mage.target.targetpointer.FixedTarget;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class Clockspinning extends CardImpl {

    public Clockspinning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));

        // Choose a counter on target permanent or suspended card. Remove that counter from that permanent or card or put another of those counters on it.
        this.getSpellAbility().addTarget(new TargetPermanentOrSuspendedCard());
        this.getSpellAbility().addEffect(new ClockspinningAddOrRemoveCounterEffect());
    }

    private Clockspinning(final Clockspinning card) {
        super(card);
    }

    @Override
    public Clockspinning copy() {
        return new Clockspinning(this);
    }
}

class ClockspinningAddOrRemoveCounterEffect extends OneShotEffect {

    ClockspinningAddOrRemoveCounterEffect() {
        super(Outcome.Removal);
        this.staticText = "Choose a counter on target permanent or suspended card. Remove that counter from that permanent or card or put another of those counters on it";
    }

    private ClockspinningAddOrRemoveCounterEffect(final ClockspinningAddOrRemoveCounterEffect effect) {
        super(effect);
    }

    @Override
    public ClockspinningAddOrRemoveCounterEffect copy() {
        return new ClockspinningAddOrRemoveCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Card target = permanent != null ? permanent : game.getCard(source.getFirstTarget());
        if (player == null || target == null) {
            return false;
        }
        Effect effect;
        if (player.chooseUse(Outcome.Neutral, "Remove a counter?", source, game)) {
            // the removal effect picks the kind itself
            effect = new RemoveCounterTargetEffect();
        } else {
            Counter counter = selectCounterType(game, source, target);
            if (counter == null) {
                return true;
            }
            effect = new AddCountersTargetEffect(counter);
        }
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        effect.apply(game, source);
        return true;
    }

    private Counter selectCounterType(Game game, Ability source, Card object) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || object.getCounters(game).isEmpty()) {
            return null;
        }
        String counterName = null;
        if (object.getCounters(game).size() > 1) {
            Choice choice = new ChoiceImpl(true);
            Set<String> choices = new LinkedHashSet<>();
            for (Counter counter : object.getCounters(game).values()) {
                if (object.getCounters(game).getCount(counter.getName()) > 0) {
                    choices.add(counter.getName());
                }
            }
            choice.setChoices(choices);
            choice.setMessage("Choose a counter type to add to " + object.getName());
            if (!controller.choose(Outcome.Neutral, choice, game)) {
                return null;
            }
            counterName = choice.getChoice();
        } else {
            for (Counter counter : object.getCounters(game).values()) {
                if (counter.getCount() > 0) {
                    counterName = counter.getName();
                }
            }
        }
        return new Counter(counterName);
    }
}
