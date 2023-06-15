package mage.cards.r;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Susucr
 */
public final class RiseOfTheWitchKing extends CardImpl {

    public RiseOfTheWitchKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{G}");
        

        // Each player sacrifices a creature. If you sacrificed a creature this way,
        // you may return another permanent card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new RiseOfTheWitchKingEffect());
    }

    private RiseOfTheWitchKing(final RiseOfTheWitchKing card) {
        super(card);
    }

    @Override
    public RiseOfTheWitchKing copy() {
        return new RiseOfTheWitchKing(this);
    }
}

class RiseOfTheWitchKingEffect extends OneShotEffect {
    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("creature");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    RiseOfTheWitchKingEffect() {
        super(Outcome.Benefit);
        staticText = "each player sacrifices a creature. If you sacrificed a creature this way, " +
                "you may return another permanent card from your graveyard to the battlefield";
    }

    private RiseOfTheWitchKingEffect(final RiseOfTheWitchKingEffect effect) {
        super(effect);
    }

    @Override
    public RiseOfTheWitchKingEffect copy() {
        return new RiseOfTheWitchKingEffect(this);
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
        controller.choose(outcome, yourGrave, target, source, game);
        Card card = controller.getGraveyard().get(target.getFirstTarget(), game);
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
