
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jonubuu
 */
public final class GroveOfTheBurnwillows extends CardImpl {

    public GroveOfTheBurnwillows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add {R} or {G}. Each opponent gains 1 life.
        Ability RedManaAbility = new RedManaAbility();
        RedManaAbility.addEffect(new GroveOfTheBurnwillowsEffect());
        this.addAbility(RedManaAbility);
        Ability GreenManaAbility = new GreenManaAbility();
        GreenManaAbility.addEffect(new GroveOfTheBurnwillowsEffect());
        this.addAbility(GreenManaAbility);
    }

    private GroveOfTheBurnwillows(final GroveOfTheBurnwillows card) {
        super(card);
    }

    @Override
    public GroveOfTheBurnwillows copy() {
        return new GroveOfTheBurnwillows(this);
    }
}

class GroveOfTheBurnwillowsEffect extends OneShotEffect {

    GroveOfTheBurnwillowsEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent gains 1 life";
    }

    private GroveOfTheBurnwillowsEffect(final GroveOfTheBurnwillowsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if(player != null) {
                player.gainLife(1, game, source);
            }
        }
        return true;
    }

    @Override
    public GroveOfTheBurnwillowsEffect copy() {
        return new GroveOfTheBurnwillowsEffect(this);
    }
}
