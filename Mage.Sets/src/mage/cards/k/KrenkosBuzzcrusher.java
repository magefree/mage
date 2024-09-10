package mage.cards.k;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author DominionSpy
 */
public final class KrenkosBuzzcrusher extends CardImpl {

    public KrenkosBuzzcrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Krenko's Buzzcrusher enters the battlefield, for each player, destroy up to one nonbasic land that player controls. For each land destroyed this way, its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KrenkosBuzzcrusherEffect()));
    }

    private KrenkosBuzzcrusher(final KrenkosBuzzcrusher card) {
        super(card);
    }

    @Override
    public KrenkosBuzzcrusher copy() {
        return new KrenkosBuzzcrusher(this);
    }
}

class KrenkosBuzzcrusherEffect extends OneShotEffect {

    KrenkosBuzzcrusherEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "for each player, destroy up to one nonbasic land that player controls. " +
                "For each land destroyed this way, its controller may search their library for a basic land card, " +
                "put it onto the battlefield tapped, then shuffle";
    }

    private KrenkosBuzzcrusherEffect(final KrenkosBuzzcrusherEffect effect) {
        super(effect);
    }

    @Override
    public KrenkosBuzzcrusherEffect copy() {
        return new KrenkosBuzzcrusherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        List<Permanent> chosenLands = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            FilterLandPermanent filter = new FilterLandPermanent("nonbasic land " + player.getName() + " controls");
            filter.add(new ControllerIdPredicate(playerId));
            filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
            TargetPermanent target = new TargetPermanent(0, 1, filter);
            target.withNotTarget(true);

            controller.chooseTarget(outcome, target, source, game);
            Permanent land = game.getPermanent(target.getFirstTarget());
            if (land != null) {
                chosenLands.add(land);
            }
        }

        List<Permanent> destroyedLands = new ArrayList<>();
        for (Permanent land : chosenLands) {
            if (land.destroy(source, game)) {
                destroyedLands.add(land);
            }
        }

        for (Permanent land : destroyedLands) {
            Player player = game.getPlayer(land.getControllerId());
            if (player == null) {
                continue;
            }
            TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A);
            if (!player.chooseUse(Outcome.PutLandInPlay, "Search your library for " + target.getDescription() + "?", source, game)) {
                continue;
            }
            if (player.searchLibrary(target, source, game)) {
                player.moveCards(game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD,
                        source, game, true, false, false, null);
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }
}
