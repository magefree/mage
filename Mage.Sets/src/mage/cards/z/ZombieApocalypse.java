
package mage.cards.z;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class ZombieApocalypse extends CardImpl {

    public ZombieApocalypse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}{B}");

        // Return all Zombie creature cards from your graveyard to the battlefield tapped, then destroy all Humans.
        this.getSpellAbility().addEffect(new ZombieApocalypseEffect());
    }

    private ZombieApocalypse(final ZombieApocalypse card) {
        super(card);
    }

    @Override
    public ZombieApocalypse copy() {
        return new ZombieApocalypse(this);
    }
}

class ZombieApocalypseEffect extends OneShotEffect {

    private static final FilterCreatureCard filterZombie = new FilterCreatureCard();

    static {
        filterZombie.add(SubType.ZOMBIE.getPredicate());
    }

    public ZombieApocalypseEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return all Zombie creature cards from your graveyard to the battlefield tapped, then destroy all Humans.";
    }

    private ZombieApocalypseEffect(final ZombieApocalypseEffect effect) {
        super(effect);
    }

    @Override
    public ZombieApocalypseEffect copy() {
        return new ZombieApocalypseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(controller.getGraveyard().getCards(filterZombie, game), Zone.BATTLEFIELD, source, game, true, false, false, null);
            game.getState().processAction(game);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(
                    new FilterPermanent(SubType.HUMAN, "Humans"), source.getControllerId(), game)) {
                permanent.destroy(source, game, false);
            }
            return true;
        }
        return false;
    }
}
