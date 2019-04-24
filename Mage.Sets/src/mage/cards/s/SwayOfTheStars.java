
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.SetPlayerLifeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SwayOfTheStars extends CardImpl {

    public SwayOfTheStars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{8}{U}{U}");

        // Each player shuffles their hand, graveyard, and permanents he or she owns into their library, then draws seven cards. Each player's life total becomes 7.
        this.getSpellAbility().addEffect(new SwayOfTheStarsEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new SetPlayerLifeAllEffect(7));

    }

    public SwayOfTheStars(final SwayOfTheStars card) {
        super(card);
    }

    @Override
    public SwayOfTheStars copy() {
        return new SwayOfTheStars(this);
    }
}

class SwayOfTheStarsEffect extends OneShotEffect {

    public SwayOfTheStarsEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles their hand, graveyard, and permanents he or she owns into their library, then draws seven cards. Each player's life total becomes 7";
    }

    public SwayOfTheStarsEffect(final SwayOfTheStarsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                player.moveCards(player.getGraveyard(), Zone.LIBRARY, source, game);
                FilterPermanent filter = new FilterPermanent();
                filter.add(new OwnerIdPredicate(playerId));
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, controller.getId(), source.getSourceId(), game)) {
                    permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }

    @Override
    public SwayOfTheStarsEffect copy() {
        return new SwayOfTheStarsEffect(this);
    }

}
