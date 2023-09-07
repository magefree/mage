package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class MausoleumSecrets extends CardImpl {

    public MausoleumSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Undergrowth — Search your library for a black card with converted mana cost equal to or less than the number of creature cards in your graveyard, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new MausoleumSecretsEffect());
    }

    private MausoleumSecrets(final MausoleumSecrets card) {
        super(card);
    }

    @Override
    public MausoleumSecrets copy() {
        return new MausoleumSecrets(this);
    }
}

class MausoleumSecretsEffect extends OneShotEffect {

    public MausoleumSecretsEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Undergrowth</i> &mdash; Search your library "
                + "for a black card with mana value less than "
                + "or equal to the number of creature cards in your graveyard, "
                + "reveal it, put it into your hand, then shuffle.";
    }

    private MausoleumSecretsEffect(final MausoleumSecretsEffect effect) {
        super(effect);
    }

    @Override
    public MausoleumSecretsEffect copy() {
        return new MausoleumSecretsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int critterCount = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
        FilterCard filter = new FilterCard("a black card with mana value less than or equal to " + critterCount);
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, critterCount + 1));
        return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true).apply(game, source);
    }
}
