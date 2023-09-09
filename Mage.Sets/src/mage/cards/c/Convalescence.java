
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Convalescence extends CardImpl {

    public Convalescence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // At the beginning of your upkeep, if you have 10 or less life, you gain 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConvalescenceEffect(), TargetController.YOU, false));
    }

    private Convalescence(final Convalescence card) {
        super(card);
    }

    @Override
    public Convalescence copy() {
        return new Convalescence(this);
    }
}

class ConvalescenceEffect extends OneShotEffect {

    public ConvalescenceEffect() {
        super(Outcome.Neutral);
        staticText = "if you have 10 or less life, you gain 1 life";
    }

    private ConvalescenceEffect(final ConvalescenceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLife() <= 10) {
            player.gainLife(1, game, source);
            return true;
        }
        return false;
    }

    @Override
    public ConvalescenceEffect copy() {
        return new ConvalescenceEffect(this);
    }
}
