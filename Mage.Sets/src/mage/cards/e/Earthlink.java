
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class Earthlink extends CardImpl {

    public Earthlink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{R}{G}");

        // At the beginning of your upkeep, sacrifice Earthlink unless you pay {2}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{2}")), TargetController.YOU, false));

        // Whenever a creature dies, that creature's controller sacrifices a land.
        this.addAbility(new DiesCreatureTriggeredAbility(new EarthlinkEffect(), false, false, true));
    }

    private Earthlink(final Earthlink card) {
        super(card);
    }

    @Override
    public Earthlink copy() {
        return new Earthlink(this);
    }
}

class EarthlinkEffect extends OneShotEffect {

    public EarthlinkEffect() {
        super(Outcome.DrawCard);
        this.staticText = "that creature's controller sacrifices a land";
    }

    public EarthlinkEffect(final EarthlinkEffect effect) {
        super(effect);
    }

    @Override
    public EarthlinkEffect copy() {
        return new EarthlinkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                Effect effect = new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "that creature's controller");
                effect.setTargetPointer(new FixedTarget(controller.getId(), game));
                effect.apply(game, source);
            }
        }
        return false;
    }
}
