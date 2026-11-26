package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author muz
 */
public final class ShaiHulud extends CardImpl {

    public ShaiHulud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // When this creature enters, destroy all artifacts and enchantments. For each player who controlled a permanent destroyed this way, search your library for a basic land card or a Desert card. Put those cards onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ShaiHuludEffect()));
    }

    private ShaiHulud(final ShaiHulud card) {
        super(card);
    }

    @Override
    public ShaiHulud copy() {
        return new ShaiHulud(this);
    }
}

class ShaiHuludEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("basic land cards and/or Desert cards");

    static {
        filter.add(Predicates.or(
            Predicates.and(
                CardType.LAND.getPredicate(),
                SuperType.BASIC.getPredicate()
            ),
            SubType.DESERT.getPredicate()
        ));
    }

    ShaiHuludEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all artifacts and enchantments. For each player who controlled a permanent destroyed this way, "
            + "search your library for a basic land card or a Desert card. Put those cards onto the battlefield tapped, then shuffle";
    }

    private ShaiHuludEffect(final ShaiHuludEffect effect) {
        super(effect);
    }

    @Override
    public ShaiHuludEffect copy() {
        return new ShaiHuludEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Set<UUID> affectedPlayers = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT, source.getControllerId(), source, game
        )) {
            UUID controllerId = permanent.getControllerId();
            if (permanent.destroy(source, game, false)) {
                affectedPlayers.add(controllerId);
            }
        }
        game.processAction();

        if (affectedPlayers.isEmpty()) {
            controller.shuffleLibrary(source, game);
            return true;
        }
        return new SearchLibraryPutInPlayEffect(
            new TargetCardInLibrary(0, affectedPlayers.size(), filter), true
        ).apply(game, source);
    }
}
