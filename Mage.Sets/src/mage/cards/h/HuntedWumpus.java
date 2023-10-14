
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class HuntedWumpus extends CardImpl {

    public HuntedWumpus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Hunted Wumpus enters the battlefield, each other player may put a creature card from their hand onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HuntedWumpusEffect(), false));

    }

    private HuntedWumpus(final HuntedWumpus card) {
        super(card);
    }

    @Override
    public HuntedWumpus copy() {
        return new HuntedWumpus(this);
    }
}

class HuntedWumpusEffect extends OneShotEffect {

    public HuntedWumpusEffect() {
        super(Outcome.Detriment);
        this.staticText = "each other player may put a creature card from their hand onto the battlefield";
    }

    private HuntedWumpusEffect(final HuntedWumpusEffect effect) {
        super(effect);
    }

    @Override
    public HuntedWumpusEffect copy() {
        return new HuntedWumpusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controller.getId())) {
                    Effect effect = new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A, true);
                    effect.setTargetPointer(new FixedTarget(playerId));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
