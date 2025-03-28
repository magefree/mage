package mage.cards.n;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NaturesRhythm extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("a creature card with mana value X or less");

    static {
        filter.add(NaturesRhythmPredicate.instance);
    }

    public NaturesRhythm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");

        // Search your library for a creature card with mana value X or less, put it onto the battlefield, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)));

        // Harmonize {X}{G}{G}{G}{G}
        this.addAbility(new HarmonizeAbility(this, "{X}{G}{G}{G}{G}"));
    }

    private NaturesRhythm(final NaturesRhythm card) {
        super(card);
    }

    @Override
    public NaturesRhythm copy() {
        return new NaturesRhythm(this);
    }
}

enum NaturesRhythmPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() <= GetXValue.instance.calculate(game, input.getSource(), null);
    }
}
