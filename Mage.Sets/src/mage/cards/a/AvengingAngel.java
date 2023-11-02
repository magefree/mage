
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
 * @author fireshoes
 */
public final class AvengingAngel extends CardImpl {

    public AvengingAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Avenging Angel dies, you may put it on top of its owner's library.
        this.addAbility(new DiesSourceTriggeredAbility(new AvengingAngelEffect(), true));
    }

    private AvengingAngel(final AvengingAngel card) {
        super(card);
    }

    @Override
    public AvengingAngel copy() {
        return new AvengingAngel(this);
    }
}

class AvengingAngelEffect extends OneShotEffect {
    
    public AvengingAngelEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may put it on the top of its owner's library";
    }
    
    private AvengingAngelEffect(final AvengingAngelEffect effect) {
        super(effect);
    }
    
    @Override
    public AvengingAngelEffect copy() {
        return new AvengingAngelEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                controller.moveCardToLibraryWithInfo(sourceCard, source, game, Zone.GRAVEYARD, true, true);
            }
            return true;
        }
        return false;
    }
}