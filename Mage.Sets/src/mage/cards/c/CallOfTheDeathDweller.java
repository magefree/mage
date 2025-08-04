package mage.cards.c;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class CallOfTheDeathDweller extends CardImpl {

    public CallOfTheDeathDweller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Return up to two target creature cards with total converted mana cost 3 or less from your graveyard to the battlefield. Put a deathtouch counter on either of them. Then put a menace counter on either of them.
        this.getSpellAbility().addEffect(new CallOfTheDeathDwellerEffect());
        this.getSpellAbility().addTarget(new CallOfTheDeathDwellerTarget());
    }

    private CallOfTheDeathDweller(final CallOfTheDeathDweller card) {
        super(card);
    }

    @Override
    public CallOfTheDeathDweller copy() {
        return new CallOfTheDeathDweller(this);
    }
}

class CallOfTheDeathDwellerEffect extends OneShotEffect {

    CallOfTheDeathDwellerEffect() {
        super(Outcome.Benefit);
        staticText = "Return up to two target creature cards with total mana value 3 or less " +
                "from your graveyard to the battlefield. Put a deathtouch counter on either of them. " +
                "Then put a menace counter on either of them.";
    }

    private CallOfTheDeathDwellerEffect(final CallOfTheDeathDwellerEffect effect) {
        super(effect);
    }

    @Override
    public CallOfTheDeathDwellerEffect copy() {
        return new CallOfTheDeathDwellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(
                source.getTargets()
                        .stream()
                        .map(Target::getTargets)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
        );
        if (player == null || cards.isEmpty()
                || !player.moveCards(cards, Zone.BATTLEFIELD, source, game)) {
            return false;
        }
        List<PermanentIdPredicate> predicates = cards
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .map(PermanentIdPredicate::new)
                .collect(Collectors.toList());
        if (predicates.isEmpty()) {
            return false;
        }
        if (predicates.size() == 1) {
            Permanent permanent = game.getPermanent(cards.stream().findFirst().orElse(null));
            if (permanent != null) {
                permanent.addCounters(CounterType.DEATHTOUCH.createInstance(), source.getControllerId(), source, game);
                permanent.addCounters(CounterType.MENACE.createInstance(), source.getControllerId(), source, game);
            }
            return true;
        }
        FilterPermanent filter = new FilterPermanent("creature to put a deathtouch counter on");
        filter.add(Predicates.or(predicates));
        Target target = new TargetPermanent(0, 1, filter, true);
        if (player.choose(outcome, target, source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(CounterType.DEATHTOUCH.createInstance(), source.getControllerId(), source, game);
            }
        }
        filter.setMessage("creature to put a menace counter on");
        target = new TargetPermanent(0, 1, filter, true);
        if (player.choose(outcome, target, source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(CounterType.MENACE.createInstance(), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}

class CallOfTheDeathDwellerTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filterStatic
            = new FilterCreatureCard("creature cards with total mana value 3 or less from your graveyard");

    CallOfTheDeathDwellerTarget() {
        super(0, 2, filterStatic, false);
    }

    private CallOfTheDeathDwellerTarget(final CallOfTheDeathDwellerTarget target) {
        super(target);
    }

    @Override
    public CallOfTheDeathDwellerTarget copy() {
        return new CallOfTheDeathDwellerTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 3, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 3, game);
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }

}
