package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author North
 */
public final class ClarionUltimatum extends CardImpl {

    public ClarionUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}{W}{W}{W}{U}{U}");

        // Choose five permanents you control. For each of those permanents, you may search your library for a card with the same name as that permanent. Put those cards onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new ClarionUltimatumEffect());
    }

    private ClarionUltimatum(final ClarionUltimatum card) {
        super(card);
    }

    @Override
    public ClarionUltimatum copy() {
        return new ClarionUltimatum(this);
    }
}

class ClarionUltimatumEffect extends OneShotEffect {

    public ClarionUltimatumEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose five permanents you control. For each of those permanents, " +
                "you may search your library for a card with the same name as that permanent. " +
                "Put those cards onto the battlefield tapped, then shuffle";
    }

    public ClarionUltimatumEffect(final ClarionUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public ClarionUltimatumEffect copy() {
        return new ClarionUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int permCount = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT,
                source.getSourceId(), source.getControllerId(), game
        );
        TargetPermanent targetPermanent = new TargetControlledPermanent(Math.max(permCount, 5));
        targetPermanent.setNotTarget(true);
        player.choose(outcome, targetPermanent, source.getSourceId(), game);
        Set<String> names = targetPermanent
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(MageObject::getName)
                .collect(Collectors.toSet());
        TargetCardInLibrary targetCardInLibrary = new ClarionUltimatumTarget(names);
        player.searchLibrary(targetCardInLibrary, source, game);
        Cards cards = new CardsImpl(targetCardInLibrary.getTargets());
        player.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
        player.shuffleLibrary(source, game);
        return true;
    }
}

class ClarionUltimatumTarget extends TargetCardInLibrary {

    private final Map<String, Integer> nameMap = new HashMap<>();

    ClarionUltimatumTarget(Set<String> names) {
        super(0, names.size(), makeFilter(names));
        this.populateNameMap(names);
    }

    private ClarionUltimatumTarget(final ClarionUltimatumTarget target) {
        super(target);
        this.nameMap.putAll(target.nameMap);
    }

    @Override
    public ClarionUltimatumTarget copy() {
        return new ClarionUltimatumTarget(this);
    }

    private static FilterCard makeFilter(Set<String> names) {
        FilterCard filter = new FilterCard();
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
