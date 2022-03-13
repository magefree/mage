package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadlyBrew extends CardImpl {

    public DeadlyBrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{G}");

        // Each player sacrifices a creature or planeswalker. If you sacrificed a permanent this way, you may return another permanent card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new DeadlyBrewEffect());
    }

    private DeadlyBrew(final DeadlyBrew card) {
        super(card);
    }

    @Override
    public DeadlyBrew copy() {
        return new DeadlyBrew(this);
    }
}

class DeadlyBrewEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    DeadlyBrewEffect() {
        super(Outcome.Benefit);
        staticText = "each player sacrifices a creature or planeswalker. If you sacrificed a permanent this way, " +
                "you may return another permanent card from your graveyard to your hand";
    }

    private DeadlyBrewEffect(final DeadlyBrewEffect effect) {
        super(effect);
    }

    @Override
    public DeadlyBrewEffect copy() {
        return new DeadlyBrewEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Permanent> toSacrifice = new ArrayList<>();
        Permanent yours = null;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(filter);
            target.setNotTarget(true);
            if (!target.canChoose(playerId, source, game)) {
                continue;
            }
            player.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                continue;
            }
            if (permanent.isControlledBy(source.getControllerId())) {
                yours = permanent;
            } else {
                toSacrifice.add(permanent);
            }
        }
        for (Permanent permanent : toSacrifice) {
            if (permanent == null) {
                continue;
            }
            permanent.sacrifice(source, game);
        }
        Cards yourGrave = new CardsImpl(controller.getGraveyard());
        yourGrave.removeIf(uuid -> !game.getCard(uuid).isPermanent(game));
        if (yours == null || !yours.sacrifice(source, game)) {
            return true;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_PERMANENT, true
        );
        controller.choose(outcome, yourGrave, target, game);
        Card card = controller.getGraveyard().get(target.getFirstTarget(), game);
        if (card != null) {
            controller.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
