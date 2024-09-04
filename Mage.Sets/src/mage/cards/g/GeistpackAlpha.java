package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class GeistpackAlpha extends CardImpl {


    public GeistpackAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());


        // When Geistpack Alpha dies, seek a permanent card with mana value equal to the number of lands you control.
        this.addAbility(new DiesSourceTriggeredAbility(new GeistpackAlphaDiesEffect()));

    }

    private GeistpackAlpha(final GeistpackAlpha card) {
        super(card);
    }

    @Override
    public GeistpackAlpha copy() {
        return new GeistpackAlpha(this);
    }
}

class GeistpackAlphaDiesEffect extends OneShotEffect {

    GeistpackAlphaDiesEffect() {
        super(Outcome.Benefit);
        staticText = " seek a permanent card with mana value equal to the number of lands you control.";
    }

    private GeistpackAlphaDiesEffect(final GeistpackAlphaDiesEffect effect) {
        super(effect);
    }

    @Override
    public GeistpackAlphaDiesEffect copy() {
        return new GeistpackAlphaDiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int lands = game.getBattlefield().countAll(StaticFilters.FILTER_LAND, game.getControllerId(source.getSourceId()), game);
        FilterPermanentCard filter = new FilterPermanentCard();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, lands));
        player.seekCard(filter, source, game);
        return true;
    }
}