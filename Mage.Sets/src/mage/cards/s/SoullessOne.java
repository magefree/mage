
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class SoullessOne extends CardImpl {

    public SoullessOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Soulless One's power and toughness are each equal to the number of Zombies on the battlefield plus the number of Zombie cards in all graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new SoullessOneDynamicCount())));
    }

    private SoullessOne(final SoullessOne card) {
        super(card);
    }

    @Override
    public SoullessOne copy() {
        return new SoullessOne(this);
    }
}

class SoullessOneDynamicCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        FilterPermanent zombiesBattlefield = new FilterPermanent("Zombies on the battlefield");
        FilterCard zombiesInGraveyard = new FilterCard("Zombie cards in all graveyards");
        zombiesBattlefield.add(SubType.ZOMBIE.getPredicate());
        zombiesInGraveyard.add(SubType.ZOMBIE.getPredicate());
        
        int count = game.getBattlefield().count(zombiesBattlefield, sourceAbility.getControllerId(), sourceAbility, game);
        for (UUID playerId : game.getState().getPlayersInRange(sourceAbility.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                count += player.getGraveyard().count(zombiesInGraveyard, game);
            }
        }
        return count;
    }

    @Override
    public SoullessOneDynamicCount copy() {
        return new SoullessOneDynamicCount();
    }
    
    @Override
    public String getMessage() {
        return "Zombies on the battlefield plus the number of Zombie cards in all graveyards";
    }

    @Override
    public String toString() {
        return "1";
    }
}
