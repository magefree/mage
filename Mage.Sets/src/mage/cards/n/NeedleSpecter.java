
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class NeedleSpecter extends CardImpl {

    public NeedleSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.SPECTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Wither
        this.addAbility(WitherAbility.getInstance());
        
        // Whenever Needle Specter deals combat damage to a player, that player discards that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new NeedleSpecterEffect(), false, true));
        
    }

    private NeedleSpecter(final NeedleSpecter card) {
        super(card);
    }

    @Override
    public NeedleSpecter copy() {
        return new NeedleSpecter(this);
    }
}

class NeedleSpecterEffect extends OneShotEffect {

        public NeedleSpecterEffect() {
            super(Outcome.Discard);
            this.staticText = "that player discards that many cards";
        }

        public NeedleSpecterEffect(final NeedleSpecterEffect effect) {
            super(effect);
        }

        @Override
        public NeedleSpecterEffect copy() {
            return new NeedleSpecterEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
                int damage = (Integer)getValue("damage");
                targetPlayer.discard(damage, false, false, source, game);
                game.informPlayers(targetPlayer.getLogName() + "discards " + damage + " card(s)");
                return true;
            }
            return false;
        }
    
}