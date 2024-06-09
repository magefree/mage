package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class VigilForTheLost extends CardImpl {

    public VigilForTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever a creature you control dies, you may pay {X}. If you do, you gain X life.
        this.addAbility(new DiesCreatureTriggeredAbility(new VigilForTheLostEffect(), true, StaticFilters.FILTER_CONTROLLED_A_CREATURE));
    }

    private VigilForTheLost(final VigilForTheLost card) {
        super(card);
    }

    @Override
    public VigilForTheLost copy() {
        return new VigilForTheLost(this);
    }

}

class VigilForTheLostEffect extends OneShotEffect {
    VigilForTheLostEffect() {
        super(Outcome.GainLife);
        staticText = "pay {X}. If you do, you gain X life";
    }

    private VigilForTheLostEffect(final VigilForTheLostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int costX = controller.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
        if (new GenericManaCost(costX).pay(source, game, source, source.getControllerId(), false, null)) {
            controller.gainLife(costX, game, source);
            return true;
        }
        return false;
    }

    @Override
    public VigilForTheLostEffect copy() {
        return new VigilForTheLostEffect(this);
    }

}
