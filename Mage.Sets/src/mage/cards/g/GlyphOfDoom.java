
package mage.cards.g;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author LevelX2 & L_J
 */
public final class GlyphOfDoom extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall creature");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public GlyphOfDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Choose target Wall creature. At this turn's next end of combat, destroy all creatures that were blocked by that creature this turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new InfoEffect("Choose target Wall creature"));
        this.getSpellAbility().addEffect(new GlyphOfDoomCreateDelayedTriggeredAbilityEffect());
        this.getSpellAbility().addWatcher(new BlockedAttackerWatcher());
    }

    private GlyphOfDoom(final GlyphOfDoom card) {
        super(card);
    }

    @Override
    public GlyphOfDoom copy() {
        return new GlyphOfDoom(this);
    }
}

class GlyphOfDoomCreateDelayedTriggeredAbilityEffect extends OneShotEffect {

    public GlyphOfDoomCreateDelayedTriggeredAbilityEffect() {
        super(Outcome.Benefit);
        this.staticText = "At this turn's next end of combat, destroy all creatures that were blocked by that creature this turn";
    }

    public GlyphOfDoomCreateDelayedTriggeredAbilityEffect(final GlyphOfDoomCreateDelayedTriggeredAbilityEffect effect) {
        super(effect);
    }

    @Override
    public GlyphOfDoomCreateDelayedTriggeredAbilityEffect copy() {
        return new GlyphOfDoomCreateDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty() && source.getFirstTarget() != null) {
            MageObjectReference mor = new MageObjectReference(source.getFirstTarget(), game);
            AtTheEndOfCombatDelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new GlyphOfDoomEffect(mor));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class GlyphOfDoomEffect extends OneShotEffect {
    
    MageObjectReference targetCreature;

    public GlyphOfDoomEffect(MageObjectReference targetCreature) {
        super(Outcome.DestroyPermanent);
        this.targetCreature = targetCreature;
    }

    public GlyphOfDoomEffect(final GlyphOfDoomEffect effect) {
        super(effect);
        targetCreature = effect.targetCreature;
    }

    @Override
    public GlyphOfDoomEffect copy() {
        return new GlyphOfDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && targetCreature != null) {
            BlockedAttackerWatcher watcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
            if (watcher != null) {
                List<Permanent> toDestroy = new ArrayList<>();
                for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                    if (!creature.getId().equals(targetCreature.getSourceId())) {
                        if (watcher.creatureHasBlockedAttacker(new MageObjectReference(creature, game), targetCreature, game)) {
                            toDestroy.add(creature);
                        }
                    }
                }
                for (Permanent creature : toDestroy) {
                    creature.destroy(source, game, false);
                }
                return true;
            }
        }
        return false;
    }
}
