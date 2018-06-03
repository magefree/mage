
package mage.cards.r;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class RiteOfFlame extends CardImpl {

    public RiteOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Add {R}{R}, then add {R} for each card named Rite of Flame in each graveyard.
        this.getSpellAbility().addEffect(new RiteOfFlameManaEffect());
    }

    public RiteOfFlame(final RiteOfFlame card) {
        super(card);
    }

    @Override
    public RiteOfFlame copy() {
        return new RiteOfFlame(this);
    }
}

class RiteOfFlameManaEffect extends ManaEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new NamePredicate("Rite of Flame"));
    }

    RiteOfFlameManaEffect() {
        super();
        staticText = "Add {R}{R}, then add {R} for each card named Rite of Flame in each graveyard";
    }

    RiteOfFlameManaEffect(final RiteOfFlameManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        int count = 0;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                count += player.getGraveyard().count(filter, game);
            }
        }
        return Mana.RedMana(count + 2);
    }

    @Override
    public RiteOfFlameManaEffect copy() {
        return new RiteOfFlameManaEffect(this);
    }

}
