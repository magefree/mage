
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ArashinSovereign extends CardImpl {

    public ArashinSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{W}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Arashin Sovereign dies, you may put it on the top or bottom of its owner's library.
        this.addAbility(new DiesSourceTriggeredAbility(new ArashinSovereignEffect(), true));
    }

    private ArashinSovereign(final ArashinSovereign card) {
        super(card);
    }

    @Override
    public ArashinSovereign copy() {
        return new ArashinSovereign(this);
    }
}

class ArashinSovereignEffect extends OneShotEffect {
    
    public ArashinSovereignEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may put it on the top or bottom of its owner's library";
    }
    
    private ArashinSovereignEffect(final ArashinSovereignEffect effect) {
        super(effect);
    }
    
    @Override
    public ArashinSovereignEffect copy() {
        return new ArashinSovereignEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                boolean onTop = controller.chooseUse(outcome, "Put " + sourceCard.getName() + " on top of it's owners library (otherwise on bottom)?", source, game);
                controller.moveCardToLibraryWithInfo(sourceCard, source, game, Zone.GRAVEYARD, onTop, true);
            }
            return true;
        }
        return false;
    }
}
