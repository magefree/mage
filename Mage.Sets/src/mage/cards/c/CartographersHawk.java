package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CartographersHawk extends CardImpl {

    public CartographersHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Cartographer's Hawk deals combat damage to a player who controls more lands than you, return it to its owner's hand. If you do, you may search your library for a Plains card, put it onto the battlefield tapped, then shuffle your library.
        this.addAbility(new CartographersHawkTriggeredAbility());
    }

    private CartographersHawk(final CartographersHawk card) {
        super(card);
    }

    @Override
    public CartographersHawk copy() {
        return new CartographersHawk(this);
    }
}

class CartographersHawkTriggeredAbility extends TriggeredAbilityImpl {

    CartographersHawkTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CartographersHawkEffect(), false);
    }

    private CartographersHawkTriggeredAbility(final CartographersHawkTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CartographersHawkTriggeredAbility copy() {
        return new CartographersHawkTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId())
                && game.getBattlefield().countAll(
                StaticFilters.FILTER_LAND, this.getControllerId(), game
        ) < game.getBattlefield().countAll(
                StaticFilters.FILTER_LAND, event.getTargetId(), game
        );
    }

    @Override
    public String getRule() {
        return "When {this} deals combat damage to a player who controls more lands than you, " +
                "return it to its owner's hand. If you do, you may search your library for a Plains card, " +
                "put it onto the battlefield tapped, then shuffle.";
    }
}

class CartographersHawkEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a Plains card");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    CartographersHawkEffect() {
        super(Outcome.Benefit);
    }

    private CartographersHawkEffect(final CartographersHawkEffect effect) {
        super(effect);
    }

    @Override
    public CartographersHawkEffect copy() {
        return new CartographersHawkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        if (!player.moveCards(permanent, Zone.HAND, source, game)) {
            return false;
        }
        if (!player.chooseUse(Outcome.PutLandInPlay, "Search your library for Plains card?", source, game)) {
            return true;
        }
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true).apply(game, source);
    }
}
