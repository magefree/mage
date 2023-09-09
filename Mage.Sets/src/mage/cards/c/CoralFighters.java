
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public final class CoralFighters extends CardImpl {

    public CoralFighters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Coral Fighters attacks and isn't blocked, look at the top card of defending player's library. You may put that card on the bottom of that player's library.
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new CoralFightersEffect(), false, true));
    }

    private CoralFighters(final CoralFighters card) {
        super(card);
    }

    @Override
    public CoralFighters copy() {
        return new CoralFighters(this);
    }
}

class CoralFightersEffect extends OneShotEffect {

    public CoralFightersEffect() {
        super(Outcome.Detriment);
        staticText = "look at the top card of defending player's library. You may put that card on the bottom of that player's library";
    }

    private CoralFightersEffect(final CoralFightersEffect effect) {
        super(effect);
    }

    @Override
    public CoralFightersEffect copy() {
        return new CoralFightersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player defendingPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if(controller != null && defendingPlayer != null) {
            Card card = defendingPlayer.getLibrary().getFromTop(game);
            if(card != null) {
                Cards cards = new CardsImpl(card);
                controller.lookAtCards("Coral Fighters", cards, game);
                if (controller.chooseUse(outcome, "Put that card on the bottom of its owner's library?", source, game)) {
                    controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, false, false);
                }
                else {
                    game.informPlayers(controller.getLogName() + " puts the card back on top of the library.");
                }
                return true;
            }
        }
        return false;
    }
}
