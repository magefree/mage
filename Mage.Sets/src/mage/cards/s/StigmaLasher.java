
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class StigmaLasher extends CardImpl {
    
    public StigmaLasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Wither
        this.addAbility(WitherAbility.getInstance());

        // Whenever Stigma Lasher deals damage to a player, that player can't gain life for the rest of the game.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new StigmaLasherEffect(), false, true));
        
    }
    
    private StigmaLasher(final StigmaLasher card) {
        super(card);
    }
    
    @Override
    public StigmaLasher copy() {
        return new StigmaLasher(this);
    }
}

class StigmaLasherEffect extends ContinuousEffectImpl {
    
    public StigmaLasherEffect() {
        super(Duration.EndOfGame, Layer.PlayerEffects, SubLayer.NA, Outcome.Neutral);
        staticText = "that player can't gain life for the rest of the game";
    }
    
    private StigmaLasherEffect(final StigmaLasherEffect effect) {
        super(effect);
    }
    
    @Override
    public StigmaLasherEffect copy() {
        return new StigmaLasherEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.targetPointer.getFirst(game, source));
        if (player != null) {
            player.setCanGainLife(false);
        }
        return true;
    }
}