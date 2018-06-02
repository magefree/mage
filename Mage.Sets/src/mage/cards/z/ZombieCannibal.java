
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author cbt33
 */
public final class ZombieCannibal extends CardImpl {

    public ZombieCannibal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Zombie Cannibal deals combat damage to a player, you may exile target card from that player's graveyard.
    this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ZombieCannibalEffect(), true, true));
    
    }

    public ZombieCannibal(final ZombieCannibal card) {
        super(card);
    }

    @Override
    public ZombieCannibal copy() {
        return new ZombieCannibal(this);
    }
}

class ZombieCannibalEffect extends OneShotEffect {
    
    public ZombieCannibalEffect() {
        super(Outcome.Exile);
        staticText = "you may exile target card from that player's graveyard.";
    }
    
    public ZombieCannibalEffect(final ZombieCannibalEffect effect) {
        super(effect);
    }
    
    @Override
    public ZombieCannibalEffect copy() {
        return new ZombieCannibalEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard();
        Player player = game.getPlayer(source.getTargets().getFirstTarget());
        if (player != null) {
            filter.add(new OwnerIdPredicate(player.getId()));
            Target target = new TargetCardInGraveyard(filter);
            game.getPermanent(target.getFirstTarget()).moveToExile(null, null, source.getSourceId(), game);
        }
        return false;
    }
    
}
