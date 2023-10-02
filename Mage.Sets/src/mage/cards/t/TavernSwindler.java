
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
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
 * @author LevelX2
 */
public final class TavernSwindler extends CardImpl {

    public TavernSwindler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}, Pay 3 life: Flip a coin. If you win the flip, you gain 6 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TavernSwindlerEffect(),new TapSourceCost());
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);
    }

    private TavernSwindler(final TavernSwindler card) {
        super(card);
    }

    @Override
    public TavernSwindler copy() {
        return new TavernSwindler(this);
    }
}

class TavernSwindlerEffect extends OneShotEffect {

    public TavernSwindlerEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, you gain 6 life";
    }

    private TavernSwindlerEffect(final TavernSwindlerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(source, game, true)) {
                game.informPlayers(controller.getLogName() + " got " + controller.gainLife(6, game, source)+ " live");
            }
        }
        return false;
    }

    @Override
    public TavernSwindlerEffect copy() {
        return new TavernSwindlerEffect(this);
    }
}