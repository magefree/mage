package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheCavesOfAndrozani extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creatures");
    private static final FilterCard filter2 = new FilterCard("a Doctor card");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter2.add(SubType.DOCTOR.getPredicate());
    }

    public TheCavesOfAndrozani(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- Put two stun counters on each of up to two target tapped creatures.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new AddCountersTargetEffect(CounterType.STUN.createInstance(2)),
                new TargetPermanent(0, 2, filter)
        );

        // II, III -- For each non-Saga permanent, choose a counter on it. You may put an additional counter of that kind on that permanent.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new TheCavesOfAndrozaniEffect()
        );

        // IV -- Search your library for a Doctor card, reveal it, put it into your hand, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter2), true)
        );
        this.addAbility(sagaAbility);
    }

    private TheCavesOfAndrozani(final TheCavesOfAndrozani card) {
        super(card);
    }

    @Override
    public TheCavesOfAndrozani copy() {
        return new TheCavesOfAndrozani(this);
    }
}

class TheCavesOfAndrozaniEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("non-Saga permanent");

    static {
        filter.add(Predicates.not(SubType.SAGA.getPredicate()));
        filter.add(CounterAnyPredicate.instance);
    }

    TheCavesOfAndrozaniEffect() {
        super(Outcome.Benefit);
        staticText = "for each non-Saga permanent, choose a counter on it. " +
                "You may put an additional counter of that kind on that permanent";
    }

    private TheCavesOfAndrozaniEffect(final TheCavesOfAndrozaniEffect effect) {
        super(effect);
    }

    @Override
    public TheCavesOfAndrozaniEffect copy() {
        return new TheCavesOfAndrozaniEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        target.withChooseHint("to add another counter to");
        player.choose(outcome, target, source, game);
        List<Permanent> permanents = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents) {
            Set<String> counterTypes = new HashSet<>(permanent.getCounters(game).keySet());
            CounterType counterType;
            switch (counterTypes.size()) {
                case 0:
                    continue;
                case 1:
                    counterType = CounterType.findByName(RandomUtil.randomFromCollection(counterTypes));
                    break;
                default:
                    Choice choice = new ChoiceImpl(true);
                    choice.setMessage("Choose a type of counter to add to " + permanent.getIdName());
                    choice.setChoices(counterTypes);
                    player.choose(outcome, choice, game);
                    counterType = CounterType.findByName(choice.getChoice());
            }
            if (counterType != null) {
                permanent.addCounters(counterType.createInstance(), source, game);
            }
        }
        return true;
    }
}
