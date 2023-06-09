package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ringsight extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("card that shares a color with a legendary creature you control");

    static {
        filter.add(RingsightPredicate.instance);
    }

    public Ringsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");

        // The Ring tempts you. Search your library for a card that shares a color with a legendary creature you control, reveal it, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true
        ));
    }

    private Ringsight(final Ringsight card) {
        super(card);
    }

    @Override
    public Ringsight copy() {
        return new Ringsight(this);
    }
}

enum RingsightPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        ObjectColor color = input.getObject().getColor(game);
        return color
                .hasColor()
                && game
                .getBattlefield()
                .getActivePermanents(filter, input.getPlayerId(), input.getSource(), game)
                .stream()
                .anyMatch(permanent -> permanent.isCreature(game) && permanent.getColor(game).shares(color));
    }
}
