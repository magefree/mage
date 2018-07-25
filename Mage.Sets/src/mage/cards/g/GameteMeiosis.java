package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author NinthWorld
 */
public final class GameteMeiosis extends CardImpl {

    public GameteMeiosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        

        // Whenever a creature dies, that creature's controller may draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new GameteMeiosisEffect(), false, false, true));
    }

    public GameteMeiosis(final GameteMeiosis card) {
        super(card);
    }

    @Override
    public GameteMeiosis copy() {
        return new GameteMeiosis(this);
    }
}

class GameteMeiosisEffect extends OneShotEffect {

    public GameteMeiosisEffect() {
        super(Outcome.DrawCard);
        this.staticText = "that creature's controller may draw a card";
    }

    public GameteMeiosisEffect(final GameteMeiosisEffect effect) {
        super(effect);
    }

    @Override
    public GameteMeiosisEffect copy() {
        return new GameteMeiosisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                if (controller.chooseUse(outcome, "Draw a card?", source, game)) {
                    controller.drawCards(1, game);
                }
                return true;
            }
        }
        return false;
    }
}