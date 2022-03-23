package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 * @author ilcartographer
 */
public final class BreakingPoint extends CardImpl {

    public BreakingPoint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Any player may have Breaking Point deal 6 damage to them. If no one does, destroy all creatures. Creatures destroyed this way can't be regenerated.
        this.getSpellAbility().addEffect(new BreakingPointDestroyEffect());
    }

    private BreakingPoint(final BreakingPoint card) {
        super(card);
    }

    @Override
    public BreakingPoint copy() {
        return new BreakingPoint(this);
    }
}

class BreakingPointDestroyEffect extends OneShotEffect {

    public BreakingPointDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Any player may have {this} deal 6 damage to them. If no one does, destroy all creatures. Creatures destroyed this way can't be regenerated.";
    }

    public BreakingPointDestroyEffect(final BreakingPointDestroyEffect effect) {
        super(effect);
    }

    @Override
    public BreakingPointDestroyEffect copy() {
        return new BreakingPointDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        StackObject spell = null;
        for (StackObject object : game.getStack()) {
            if (object instanceof Spell && object.getSourceId().equals(source.getSourceId())) {
                spell = object;
            }
        }
        if (spell != null) {
            boolean destroyCreatures = true;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.chooseUse(Outcome.Detriment, "Have " + spell.getLogName() + " deal 6 damage to you?", source, game)) {
                    destroyCreatures = false;
                    player.damage(6, source.getSourceId(), source, game);
                    game.informPlayers(player.getLogName() + " has " + spell.getName() + " deal 6 to them");
                }
            }
            if (destroyCreatures) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(FILTER_PERMANENT_CREATURES, source.getControllerId(), source, game)) {
                    permanent.destroy(source, game, true);
                }
            }
            return destroyCreatures;
        }
        return false;
    }
}
