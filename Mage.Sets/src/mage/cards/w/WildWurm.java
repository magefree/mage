
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
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
public final class WildWurm extends CardImpl {

    public WildWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Wild Wurm enters the battlefield, flip a coin. If you lose the flip, return Wild Wurm to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WildWurmEffect(), false));
    }

    private WildWurm(final WildWurm card) {
        super(card);
    }

    @Override
    public WildWurm copy() {
        return new WildWurm(this);
    }
}

class WildWurmEffect extends OneShotEffect {

    public WildWurmEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you lose the flip, return {this} to its owner's hand";
    }

    private WildWurmEffect(final WildWurmEffect effect) {
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
    public WildWurmEffect copy() {
        return new WildWurmEffect(this);
    }
}
