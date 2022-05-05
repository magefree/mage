
package mage.cards.i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class InfernalOffering extends CardImpl {

    public InfernalOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Choose an opponent. You and that player each sacrifice a creature. Each player who sacrificed a creature this way draws two cards.
        this.getSpellAbility().addEffect(new InfernalOfferingSacrificeEffect());

        // Choose an opponent. Return a creature card from your graveyard to the battlefield, then that player returns a creature card from their graveyard to the battlefield.
        this.getSpellAbility().addEffect(new InfernalOfferingReturnEffect().concatBy("<br>"));
    }

    private InfernalOffering(final InfernalOffering card) {
        super(card);
    }

    @Override
    public InfernalOffering copy() {
        return new InfernalOffering(this);
    }
}

class InfernalOfferingSacrificeEffect extends OneShotEffect {

    InfernalOfferingSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an opponent. You and that player each sacrifice a creature. Each player who sacrificed a creature this way draws two cards";
    }

    InfernalOfferingSacrificeEffect(final InfernalOfferingSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public InfernalOfferingSacrificeEffect copy() {
        return new InfernalOfferingSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), source, game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                //Choose creatures to sacrifice
                Map<UUID, UUID> toSacrifice = new HashMap<>(2);
                for (UUID playerId : game.getState().getPlayersInRange(player.getId(), game)) {
                    if (playerId.equals(player.getId()) || playerId.equals(opponent.getId())) {
                        target = new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent(), true);
                        if (target.choose(Outcome.Sacrifice, playerId, source.getControllerId(), source, game)) {
                            toSacrifice.put(playerId, target.getFirstTarget());
                        }
                    }
                }
                //Sacrifice the chosen creatures
                List<UUID> toDraw = new ArrayList<>(2);
                for (Entry<UUID, UUID> entry : toSacrifice.entrySet()) {
                    Permanent permanent = game.getPermanent(entry.getValue());
                    if (permanent != null) {
                        if (permanent.sacrifice(source, game)) {
                            toDraw.add(entry.getKey());
                        }
                    }
                }
                //Draw cards if creatures have been sacrificed
                for (UUID playerId : toDraw) {
                    Player playerToDraw = game.getPlayer(playerId);
                    if (playerToDraw != null) {
                        playerToDraw.drawCards(2, source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class InfernalOfferingReturnEffect extends OneShotEffect {

    InfernalOfferingReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose an opponent. Return a creature card from your graveyard to the battlefield, then that player returns a creature card from their graveyard to the battlefield";
    }

    InfernalOfferingReturnEffect(final InfernalOfferingReturnEffect effect) {
        super(effect);
    }

    @Override
    public InfernalOfferingReturnEffect copy() {
        return new InfernalOfferingReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.PutCreatureInPlay, source.getControllerId(), source.getSourceId(), source, game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
            if (target.choose(Outcome.PutCreatureInPlay, controller.getId(), source.getSourceId(), source, game)) {
                Card card = controller.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            if (opponent != null) {
                target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
                if (target.choose(Outcome.PutCreatureInPlay, opponent.getId(), source.getSourceId(), source, game)) {
                    Card card = opponent.getGraveyard().get(target.getFirstTarget(), game);
                    if (card != null) {
                        opponent.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
