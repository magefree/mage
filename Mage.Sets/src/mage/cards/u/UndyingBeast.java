
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
 * @author fireshoes
 */
public final class UndyingBeast extends CardImpl {

    public UndyingBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Undying Beast dies, put it on top of its owner's library.
        this.addAbility(new DiesTriggeredAbility(new UndyingBeastEffect()));
    }

    public UndyingBeast(final UndyingBeast card) {
        super(card);
    }

    @Override
    public UndyingBeast copy() {
        return new UndyingBeast(this);
    }
}

class UndyingBeastEffect extends OneShotEffect {

    public UndyingBeastEffect() {
        super(Outcome.ReturnToHand);
        staticText = "put it on top of its owner's library";
    }

    public UndyingBeastEffect(final UndyingBeastEffect effect) {
        super(effect);
    }

    @Override
    public UndyingBeastEffect copy() {
        return new UndyingBeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            Player owner = game.getPlayer(card.getOwnerId());
            owner.getGraveyard().remove(card);
            return card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
        }
        return true;
    }
}
