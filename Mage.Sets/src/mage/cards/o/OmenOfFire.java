
package mage.cards.o;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class OmenOfFire extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Islands");
    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public OmenOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}{R}");

        // Return all Islands to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter));
        
        // Each player sacrifices a Plains or a white permanent for each white permanent they control.
        this.getSpellAbility().addEffect(new OmenOfFireEffect());
    }

    private OmenOfFire(final OmenOfFire card) {
        super(card);
    }

    @Override
    public OmenOfFire copy() {
        return new OmenOfFire(this);
    }
}

class OmenOfFireEffect extends OneShotEffect {

    private static final FilterPermanent filterWhite1 = new FilterPermanent("Plains or white permanent");
    static {
        filterWhite1.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                new ColorPredicate(ObjectColor.WHITE)));
    }

    private static final FilterPermanent filterWhite2 = new FilterPermanent("white permanent");
    static {
        filterWhite2.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public OmenOfFireEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player sacrifices a Plains or a white permanent for each white permanent they control";
    }

    private OmenOfFireEffect(final OmenOfFireEffect effect) {
        super(effect);
    }

    @Override
    public OmenOfFireEffect copy() {
        return new OmenOfFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int whitePermanents = game.getBattlefield().countAll(filterWhite2, playerId, game);
                    if (whitePermanents > 0) {
                        Effect effect = new SacrificeEffect(filterWhite1, whitePermanents, "");
                        effect.setTargetPointer(new FixedTarget(playerId));
                        effect.apply(game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
