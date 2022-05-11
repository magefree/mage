package mage.cards.e;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FishToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ExoticPets extends CardImpl {

    public ExoticPets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{U}");

        // Create two 1/1 blue Fish creature tokens with "This creature can't be blocked." Then for each kind of counter among creatures you control, put a counter of that kind on either of those tokens.
        this.getSpellAbility().addEffect(new ExoticPetsEffect());
    }

    private ExoticPets(final ExoticPets card) {
        super(card);
    }

    @Override
    public ExoticPets copy() {
        return new ExoticPets(this);
    }
}

class ExoticPetsEffect extends OneShotEffect {

    ExoticPetsEffect() {
        super(Outcome.Benefit);
        staticText = "create two 1/1 blue Fish creature tokens with \"This creature can't be blocked.\" " +
                "Then for each kind of counter among creatures you control, " +
                "put a counter of that kind on either of those tokens";
    }

    private ExoticPetsEffect(final ExoticPetsEffect effect) {
        super(effect);
    }

    @Override
    public ExoticPetsEffect copy() {
        return new ExoticPetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Token token = new FishToken();
        token.putOntoBattlefield(2, game, source);
        List<Permanent> permanents = token
                .getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        Set<CounterType> counterTypes = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                ).stream()
                .map(permanent -> permanent.getCounters(game))
                .map(HashMap::keySet)
                .flatMap(Collection::stream)
                .distinct()
                .map(CounterType::findByName)
                .collect(Collectors.toSet());
        if (counterTypes.isEmpty()) {
            return true;
        }
        if (permanents.size() == 1) {
            Permanent permanent = permanents.get(0);
            for (CounterType counterType : counterTypes) {
                permanent.addCounters(counterType.createInstance(), source, game);
            }
            return true;
        }
        FilterPermanent filter = new FilterPermanent("creature");
        filter.add(Predicates.or(permanents
                .stream()
                .map(MageItem::getId)
                .map(PermanentIdPredicate::new)
                .collect(Collectors.toSet())));
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        for (CounterType counterType : counterTypes) {
            target.clearChosen();
            target.withChooseHint("to add " + counterType.getArticle() + ' ' + counterType + " counter to");
            player.choose(Outcome.BoostCreature, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(counterType.createInstance(), source, game);
            }
        }
        return true;
    }
}
