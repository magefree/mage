
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class AlleyGrifters extends CardImpl {

    public AlleyGrifters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Alley Grifters becomes blocked, defending player discards a card.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new AlleyGriftersDiscardEffect(), false));
    }

    public AlleyGrifters(final AlleyGrifters card) {
        super(card);
    }

    @Override
    public AlleyGrifters copy() {
        return new AlleyGrifters(this);
    }
}

class AlleyGriftersDiscardEffect extends OneShotEffect {

    public AlleyGriftersDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "defending player discards a card";
    }

    public AlleyGriftersDiscardEffect(final AlleyGriftersDiscardEffect effect) {
        super(effect);
    }

    @Override
    public AlleyGriftersDiscardEffect copy() {
        return new AlleyGriftersDiscardEffect(this);
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
