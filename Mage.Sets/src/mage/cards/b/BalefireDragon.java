
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class BalefireDragon extends CardImpl {

    public BalefireDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Balefire Dragon deals combat damage to a player, it deals that much damage to each creature that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new BalefireDragonEffect(), false, true));

    }

    private BalefireDragon(final BalefireDragon card) {
        super(card);
    }

    @Override
    public BalefireDragon copy() {
        return new BalefireDragon(this);
    }
}

class BalefireDragonEffect extends OneShotEffect {

    public BalefireDragonEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to each creature that player controls";
    }

    public BalefireDragonEffect(final BalefireDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
                    creature.damage(amount, source.getSourceId(), source, game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public BalefireDragonEffect copy() {
        return new BalefireDragonEffect(this);
    }

}
