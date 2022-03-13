package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author North
 */
public final class DoublingChant extends CardImpl {

    public DoublingChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // For each creature you control, you may search your library for a creature card with the same name as that creature.
        // Put those cards onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new DoublingChantEffect());
    }

    private DoublingChant(final DoublingChant card) {
        super(card);
    }

    @Override
    public DoublingChant copy() {
        return new DoublingChant(this);
    }
}

class DoublingChantEffect extends OneShotEffect {

    public DoublingChantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "For each creature you control, " +
                "you may search your library for a creature card with the same name as that creature. " +
                "Put those cards onto the battlefield, then shuffle";
    }

    public DoublingChantEffect(final DoublingChantEffect effect) {
        super(effect);
    }

    @Override
    public DoublingChantEffect copy() {
        return new DoublingChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<String> names = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        )
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getName)
                .collect(Collectors.toSet());
        TargetCardInLibrary targetCardInLibrary = new DoublingChantTarget(names);
        player.searchLibrary(targetCardInLibrary, source, game);
        Cards cards = new CardsImpl(targetCardInLibrary.getTargets());
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}

class DoublingChantTarget extends TargetCardInLibrary {

    private final Map<String, Integer> nameMap = new HashMap<>();

    DoublingChantTarget(Set<String> names) {
        super(0, names.size(), makeFilter(names));
        this.populateNameMap(names);
    }

    private DoublingChantTarget(final DoublingChantTarget target) {
        super(target);
        this.nameMap.putAll(target.nameMap);
    }

    @Override
    public DoublingChantTarget copy() {
        return new DoublingChantTarget(this);
    }

    private static FilterCard makeFilter(Set<String> names) {
        FilterCard filter = new FilterCreatureCard();
        filter.add(Predicates.or(names.stream().map(name -> new NamePredicate(name)).collect(Collectors.toSet())));
        return filter;
    }

    private void populateNameMap(Set<String> names) {
        names.stream().forEach(name -> this.nameMap.compute(name, CardUtil::setOrIncrementValue));
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        Map<String, Integer> alreadyChosen = new HashMap<>();
        this.getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(MageObject::getName)
                .forEach(name -> alreadyChosen.compute(name, CardUtil::setOrIncrementValue));
        return nameMap.getOrDefault(card.getName(), 0)
                > alreadyChosen.getOrDefault(card.getName(), 0);
    }
}
