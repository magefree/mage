
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SlateStreetRuffian extends CardImpl {

    public SlateStreetRuffian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);


        // Whenever Slate Street Ruffian becomes blocked, defending player discards a card.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new SlateStreetRuffianDiscardEffect(), false));
    }

    public SlateStreetRuffian(final SlateStreetRuffian card) {
        super(card);
    }

    @Override
    public SlateStreetRuffian copy() {
        return new SlateStreetRuffian(this);
    }
}

class SlateStreetRuffianDiscardEffect extends OneShotEffect {

    public SlateStreetRuffianDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "defending player discards a card";
    }

    public SlateStreetRuffianDiscardEffect(final SlateStreetRuffianDiscardEffect effect) {
        super(effect);
    }

    @Override
    public SlateStreetRuffianDiscardEffect copy() {
        return new SlateStreetRuffianDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent blockingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (blockingCreature != null) {
            Player opponent = game.getPlayer(blockingCreature.getControllerId());
            if (opponent != null) {
                opponent.discard(1, false, source, game);
                return true;
            }
        }
        return false;
    }
}
