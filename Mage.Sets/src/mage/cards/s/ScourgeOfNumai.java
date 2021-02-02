
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ScourgeOfNumai extends CardImpl {

    public ScourgeOfNumai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you lose 2 life if you don't control an Ogre.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ScourgeOfNumaiEffect(), TargetController.YOU, false));        
    }

    private ScourgeOfNumai(final ScourgeOfNumai card) {
        super(card);
    }

    @Override
    public ScourgeOfNumai copy() {
        return new ScourgeOfNumai(this);
    }
}

class ScourgeOfNumaiEffect extends OneShotEffect {
    
    public ScourgeOfNumaiEffect() {
        super(Outcome.LoseLife);
        this.staticText = "you lose 2 life if you don't control an Ogre.";
    }
    
    public ScourgeOfNumaiEffect(final ScourgeOfNumaiEffect effect) {
        super(effect);
    }
    
    @Override
    public ScourgeOfNumaiEffect copy() {
        return new ScourgeOfNumaiEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getBattlefield().countAll(new FilterCreaturePermanent(SubType.OGRE, "Ogre"), source.getControllerId(), game) < 1) {
                controller.loseLife(2, game, source, false);
            }
            return true;
        }
        return false;
    }
}
