
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class ViashinoSandswimmer extends CardImpl {

    public ViashinoSandswimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {R}: Flip a coin. If you win the flip, return Viashino Sandswimmer to its owner's hand. If you lose the flip, sacrifice Viashino Sandswimmer.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ViashinoSandswimmerEffect(), new ManaCostsImpl<>("{R}")));
    }

    private ViashinoSandswimmer(final ViashinoSandswimmer card) {
        super(card);
    }

    @Override
    public ViashinoSandswimmer copy() {
        return new ViashinoSandswimmer(this);
    }
}

class ViashinoSandswimmerEffect extends OneShotEffect {

    public ViashinoSandswimmerEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, return {this} to its owner's hand. If you lose the flip, sacrifice {this}";
    }

    public ViashinoSandswimmerEffect(ViashinoSandswimmerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (controller.flipCoin(source, game, true)) {
                new ReturnToHandSourceEffect().apply(game, source);
                return true;
            } else {
                new SacrificeSourceEffect().apply(game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public ViashinoSandswimmerEffect copy() {
        return new ViashinoSandswimmerEffect(this);
    }
}
