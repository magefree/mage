
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class ScoriaWurm extends CardImpl {

    public ScoriaWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of your upkeep, flip a coin. If you lose the flip, return Scoria Wurm to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ScoriaWurmEffect(), TargetController.YOU, false));
    }

    private ScoriaWurm(final ScoriaWurm card) {
        super(card);
    }

    @Override
    public ScoriaWurm copy() {
        return new ScoriaWurm(this);
    }
}

class ScoriaWurmEffect extends OneShotEffect {

    public ScoriaWurmEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you lose the flip, return {this} to its owner's hand";
    }

    private ScoriaWurmEffect(final ScoriaWurmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (controller.flipCoin(source, game, true)) {
                return true;
            } else {
                new ReturnToHandSourceEffect().apply(game, source);
                return true;
                }
            }
        return false;
    }

    @Override
    public ScoriaWurmEffect copy() {
        return new ScoriaWurmEffect(this);
    }
}
