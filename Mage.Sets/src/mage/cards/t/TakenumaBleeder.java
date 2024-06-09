
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TakenumaBleeder extends CardImpl {

    public TakenumaBleeder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Takenuma Bleeder attacks or blocks, you lose 1 life if you don't control a Demon.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new TakenumaBleederEffect(), false));
        
    }

    private TakenumaBleeder(final TakenumaBleeder card) {
        super(card);
    }

    @Override
    public TakenumaBleeder copy() {
        return new TakenumaBleeder(this);
    }
}

class TakenumaBleederEffect extends OneShotEffect {
    
    public TakenumaBleederEffect() {
        super(Outcome.LoseLife);
        this.staticText = "you lose 1 life if you don't control a Demon";
    }
    
    private TakenumaBleederEffect(final TakenumaBleederEffect effect) {
        super(effect);
    }
    
    @Override
    public TakenumaBleederEffect copy() {
        return new TakenumaBleederEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getBattlefield().countAll(new FilterCreaturePermanent(SubType.DEMON, "Demon"), source.getControllerId(), game) < 1) {
                controller.loseLife(1, game, source, false);
            }
            return true;
        }
        return false;
    }
}
