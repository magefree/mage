
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
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
 * @author fireshoes
 */
public final class MijaeDjinn extends CardImpl {

    public MijaeDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}{R}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Whenever Mijae Djinn attacks, flip a coin. If you lose the flip, remove Mijae Djinn from combat and tap it.
        this.addAbility(new AttacksTriggeredAbility(new MijaeDjinnEffect(), false));
    }

    private MijaeDjinn(final MijaeDjinn card) {
        super(card);
    }

    @Override
    public MijaeDjinn copy() {
        return new MijaeDjinn(this);
    }
}

class MijaeDjinnEffect extends OneShotEffect {

    public MijaeDjinnEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you lose the flip, remove {this} from combat and tap it";
    }

    private MijaeDjinnEffect(final MijaeDjinnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(source.getSourceId());
        if (controller != null && creature != null) {
            if (controller.flipCoin(source, game, true)) {
                return true;
            } else {
                creature.removeFromCombat(game);
                creature.tap(source, game);
                return true;
                }
            }
        return false;
    }

    @Override
    public MijaeDjinnEffect copy() {
        return new MijaeDjinnEffect(this);
    }
}
