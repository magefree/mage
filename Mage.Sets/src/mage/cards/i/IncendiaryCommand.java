
package mage.cards.i;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetNonBasicLandPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class IncendiaryCommand extends CardImpl {

    public IncendiaryCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Incendiary Command deals 4 damage to target player;
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        // or Incendiary Command deals 2 damage to each creature;
        Mode mode = new Mode();
        mode.getEffects().add(new DamageAllEffect(2, new FilterCreaturePermanent()));
        this.getSpellAbility().getModes().addMode(mode);
        // or destroy target nonbasic land;
        mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetNonBasicLandPermanent());
        this.getSpellAbility().getModes().addMode(mode);
        // or each player discards all the cards in their hand, then draws that many cards.
        mode = new Mode();
        mode.getEffects().add(new IncendiaryCommandDrawEffect());
        this.getSpellAbility().getModes().addMode(mode);

    }

    public IncendiaryCommand(final IncendiaryCommand card) {
        super(card);
    }

    @Override
    public IncendiaryCommand copy() {
        return new IncendiaryCommand(this);
    }
}

class IncendiaryCommandDrawEffect extends OneShotEffect {

    public IncendiaryCommandDrawEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player discards all the cards in their hand, then draws that many cards";
    }

    public IncendiaryCommandDrawEffect(final IncendiaryCommandDrawEffect effect) {
        super(effect);
    }

    @Override
    public IncendiaryCommandDrawEffect copy() {
        return new IncendiaryCommandDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> cardsToDraw = new HashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsInHand = player.getHand().size();
                    player.discard(cardsInHand, false, source, game);
                    if (cardsInHand > 0) {
                        cardsToDraw.put(playerId, cardsInHand);
                    }
                }
            }
            for (UUID playerId : cardsToDraw.keySet()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.drawCards(cardsToDraw.get(playerId), game);
                }
            }
            return true;
        }
        return false;
    }
}
