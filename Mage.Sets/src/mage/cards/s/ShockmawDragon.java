package mage.cards.s;

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
 * @author jeffwadsworth
 */
public final class ShockmawDragon extends CardImpl {

    public ShockmawDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Shockmaw Dragon deals combat damage to a player, it deals 1 damage to each creature that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ShockmawDragonEffect(), false, true));
    }

    private ShockmawDragon(final ShockmawDragon card) {
        super(card);
    }

    @Override
    public ShockmawDragon copy() {
        return new ShockmawDragon(this);
    }
}

class ShockmawDragonEffect extends OneShotEffect {

    public ShockmawDragonEffect() {
        super(Outcome.Damage);
        staticText = "it deals 1 damage to each creature that player controls";
    }

    public ShockmawDragonEffect(final ShockmawDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
                    creature.damage(1, source.getSourceId(), source, game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ShockmawDragonEffect copy() {
        return new ShockmawDragonEffect(this);
    }

}
