package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author NinthWorld
 */
public final class PsionicStorm extends CardImpl {

    public PsionicStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        

        // Each player chooses a number of creatures he or she controls equal to the number of creatures controlled by the player with the fewest, then sacrifices the rest.
        this.getSpellAbility().addEffect(new PsionicStormEffect());
    }

    public PsionicStorm(final PsionicStorm card) {
        super(card);
    }

    @Override
    public PsionicStorm copy() {
        return new PsionicStorm(this);
    }
}

// From the creature part of Balance
class PsionicStormEffect extends OneShotEffect {

    PsionicStormEffect() {
        super(Outcome.Sacrifice);
        staticText = "Each player chooses a number of creatures he or she controls equal to the number of creatures controlled by the player with the fewest, then sacrifices the rest";
    }

    PsionicStormEffect(final PsionicStormEffect effect) {
        super(effect);
    }

    @Override
    public PsionicStormEffect copy() {
        return new PsionicStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int minCreature = Integer.MAX_VALUE;
            Cards creaturesToSacrifice = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), player.getId(), game);
                    if (count < minCreature) {
                        minCreature = count;
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetControlledPermanent target = new TargetControlledPermanent(minCreature, minCreature, new FilterControlledCreaturePermanent("creatures to keep"), true);
                    if (target.choose(Outcome.Sacrifice, player.getId(), source.getSourceId(), game)) {
                        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), player.getId(), source.getSourceId(), game)) {
                            if (permanent != null && !target.getTargets().contains(permanent.getId())) {
                                creaturesToSacrifice.add(permanent);
                            }
                        }
                    }
                }
            }

            for (UUID cardId : creaturesToSacrifice) {
                Permanent permanent = game.getPermanent(cardId);
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }
}