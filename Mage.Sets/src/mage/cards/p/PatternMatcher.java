package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatternMatcher extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("a creature card with the same name as another creature you control");

    static {
        filter.add(RegularExpression.instance);
    }

    public PatternMatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Pattern Matcher enters the battlefield, you may search your library for a creature card with the same name as another creature you control, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));
    }

    private PatternMatcher(final PatternMatcher card) {
        super(card);
    }

    @Override
    public PatternMatcher copy() {
        return new PatternMatcher(this);
    }
}

enum RegularExpression implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return game
                .getState()
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES,
                        input.getPlayerId(), input.getSource(), game
                )
                .stream()
                .anyMatch(permanent -> permanent.sharesName(input.getObject(), game));
    }
}
