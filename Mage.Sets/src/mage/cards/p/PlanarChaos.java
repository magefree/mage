
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class PlanarChaos extends CardImpl {

    public PlanarChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of your upkeep, flip a coin. If you lose the flip, sacrifice Planar Chaos.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PlanarChaosUpkeepEffect(), TargetController.YOU, false));

        // Whenever a player casts a spell, that player flips a coin. If they lose the flip, counter that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(new PlanarChaosCastAllEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL));
    }

    private PlanarChaos(final PlanarChaos card) {
        super(card);
    }

    @Override
    public PlanarChaos copy() {
        return new PlanarChaos(this);
    }
}

class PlanarChaosUpkeepEffect extends OneShotEffect {

    PlanarChaosUpkeepEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you lose the flip, sacrifice {this}";
    }

    private PlanarChaosUpkeepEffect(final PlanarChaosUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.flipCoin(source, game, true)) {
                Permanent perm = game.getPermanent(source.getSourceId());
                if (perm != null) {
                    perm.sacrifice(source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public PlanarChaosUpkeepEffect copy() {
        return new PlanarChaosUpkeepEffect(this);
    }
}

class PlanarChaosCastAllEffect extends OneShotEffect {

    public PlanarChaosCastAllEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player flips a coin. If they lose the flip, counter that spell";
    }

    private PlanarChaosCastAllEffect(final PlanarChaosCastAllEffect effect) {
        super(effect);
    }

    @Override
    public PlanarChaosCastAllEffect copy() {
        return new PlanarChaosCastAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            spell = (Spell) game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.STACK);
        }
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && spell != null) {
            Player caster = game.getPlayer(spell.getControllerId());
            if (caster != null) {
                if (!caster.flipCoin(source, game, true)) {
                    game.informPlayers(sourceObject.getLogName() + ": " + spell.getLogName() + " countered");
                    game.getStack().counter(getTargetPointer().getFirst(game, source), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
