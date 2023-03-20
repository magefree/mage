package mage.cards.c;

import mage.abilities.Ability;
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

import java.util.HashSet;
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

    ClockspinningAddOrRemoveCounterEffect(final ClockspinningAddOrRemoveCounterEffect effect) {
        super(effect);
    }

    @Override
    public ClockspinningAddOrRemoveCounterEffect copy() {
        return new ClockspinningAddOrRemoveCounterEffect(this);
    }

    private Counter selectCounterType(Game game, Ability source, Permanent permanent) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !permanent.getCounters(game).isEmpty()) {
            String counterName = null;
            if (permanent.getCounters(game).size() > 1) {
                Choice choice = new ChoiceImpl(true);
                Set<String> choices = new LinkedHashSet<>();
                for (Counter counter : permanent.getCounters(game).values()) {
                    if (permanent.getCounters(game).getCount(counter.getName()) > 0) {
                        choices.add(counter.getName());
                    }
                }
                choice.setChoices(choices);
                choice.setMessage("Choose a counter type to add to " + permanent.getName());
                if (controller.choose(Outcome.Neutral, choice, game)) {
                    counterName = choice.getChoice();
                } else {
                    return null;
                }

            } else {
                for (Counter counter : permanent.getCounters(game).values()) {
                    if (counter.getCount() > 0) {
                        counterName = counter.getName();
                    }
                }
            }
            return new Counter(counterName);
        }
        return null;
    }

    private Counter selectCounterType(Game game, Ability source, Card card) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !card.getCounters(game).isEmpty()) {
            String counterName = null;
            if (card.getCounters(game).size() > 1) {
                Choice choice = new ChoiceImpl(true);
                Set<String> choices = new LinkedHashSet<>();
                for (Counter counter : card.getCounters(game).values()) {
                    if (card.getCounters(game).getCount(counter.getName()) > 0) {
                        choices.add(counter.getName());
                    }
                }
                choice.setChoices(choices);
                choice.setMessage("Choose a counter type to add to " + card.getName());
                if (controller.choose(Outcome.Neutral, choice, game)) {
                    counterName = choice.getChoice();
                } else {
                    return null;
                }
            } else {
                for (Counter counter : card.getCounters(game).values()) {
                    if (counter.getCount() > 0) {
                        counterName = counter.getName();
                    }
                }
            }
            return new Counter(counterName);
        }
        return null;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());

        if (player != null && permanent != null) {
            if (player.chooseUse(Outcome.Neutral, "Remove a counter?", source, game)) {
                RemoveCounterTargetEffect effect = new RemoveCounterTargetEffect();
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                effect.apply(game, source);
            } else {
                Counter counter = selectCounterType(game, source, permanent);

                if (counter != null) {
                    AddCountersTargetEffect effect = new AddCountersTargetEffect(counter);
                    effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                    effect.apply(game, source);
                }
            }
            return true;
        }

        Card card = game.getCard(source.getFirstTarget());
        if (player != null && card != null) {
            if (player.chooseUse(Outcome.Neutral, "Remove a counter?", source, game)) {
                Counter counter = selectCounterType(game, source, card);
                RemoveCounterTargetEffect effect = new RemoveCounterTargetEffect(counter);
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                effect.apply(game, source);
            } else {
                Counter counter = selectCounterType(game, source, card);
                if (counter != null) {
                    AddCountersTargetEffect effect = new AddCountersTargetEffect(counter);
                    effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                    effect.apply(game, source);
                }
            }
            return true;
        }

        return false;
    }
}
