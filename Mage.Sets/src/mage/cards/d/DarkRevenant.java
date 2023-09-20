
package mage.cards.d;

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
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DarkRevenant extends CardImpl {

    public DarkRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dark Revenant dies, put it on top of its owner's library.
        this.addAbility(new DiesSourceTriggeredAbility(new DarkRevenantEffect()));
    }

    private DarkRevenant(final DarkRevenant card) {
        super(card);
    }

    @Override
    public DarkRevenant copy() {
        return new DarkRevenant(this);
    }
}

class DarkRevenantEffect extends OneShotEffect {

    public DarkRevenantEffect() {
        super(Outcome.ReturnToHand);
        staticText = "put it on top of its owner's library";
    }

    private DarkRevenantEffect(final DarkRevenantEffect effect) {
        super(effect);
    }

    @Override
    public DarkRevenantEffect copy() {
        return new DarkRevenantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            Player controller = game.getPlayer(source.getControllerId());
            if(controller != null) {
                return controller.putCardsOnTopOfLibrary(card, game, source, true);
            }
        }
        return true;
    }
}
