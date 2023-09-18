package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class PatternMatcher extends CardImpl {

    public PatternMatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Pattern Matcher enters the battlefield, you may search your library for a creature card with the same name as another creature you control, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RegularExpression(), true));
    }

    private PatternMatcher(final PatternMatcher card) {
        super(card);
    }

    @Override
    public PatternMatcher copy() {
        return new PatternMatcher(this);
    }
}

class RegularExpression extends OneShotEffect {

    RegularExpression() {
        super(Outcome.Benefit);
        staticText = "search your library for a card with the same name as another creature you control, " +
                "reveal it, put it into your hand, then shuffle.";
    }

    private RegularExpression(final RegularExpression effect) {
        super(effect);
    }

    @Override
    public RegularExpression copy() {
        return new RegularExpression(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<NamePredicate> predicates = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE,
                        source.getControllerId(), source, game
                ).stream()
                .map(Permanent::getName)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .map(NamePredicate::new)
                .collect(Collectors.toList());
        FilterCard filter
                = new FilterCard("a creature card with the same name as another creature you control");
        filter.add(Predicates.or(predicates));
        return new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true
        ).apply(game, source);
    }
}
