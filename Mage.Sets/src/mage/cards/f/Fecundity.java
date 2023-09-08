package mage.cards.f;

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
 * @author LevelX2
 */
public final class Fecundity extends CardImpl {

    public Fecundity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever a creature dies, that creature's controller may draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new FecundityEffect(), false, false, true));
    }

    private Fecundity(final Fecundity card) {
        super(card);
    }

    @Override
    public Fecundity copy() {
        return new Fecundity(this);
    }
}

class FecundityEffect extends OneShotEffect {

    public FecundityEffect() {
        super(Outcome.DrawCard);
        this.staticText = "that creature's controller may draw a card";
    }

    private FecundityEffect(final FecundityEffect effect) {
        super(effect);
    }

    @Override
    public FecundityEffect copy() {
        return new FecundityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(this.getTargetPointer()
                // Card can be moved again (e.g. commander replacement) so we need the row id from fixed target to check
                .getFixedTarget(game, source).getTarget(), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                if (controller.chooseUse(outcome, "Draw a card?", source, game)) {
                    controller.drawCards(1, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
