
package mage.cards.v;

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
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author LevelX2
 */
public final class VenomousBreath extends CardImpl {

    public VenomousBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Choose target creature. At this turn's next end of combat, destroy all creatures that blocked or were blocked by it this turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new InfoEffect("Choose target creature"));
        this.getSpellAbility().addEffect(new VenomousBreathCreateDelayedTriggeredAbilityEffect());
        this.getSpellAbility().addWatcher(new BlockedAttackerWatcher());
    }

    private VenomousBreath(final VenomousBreath card) {
        super(card);
    }

    @Override
    public VenomousBreath copy() {
        return new VenomousBreath(this);
    }
}

class VenomousBreathCreateDelayedTriggeredAbilityEffect extends OneShotEffect {

    public VenomousBreathCreateDelayedTriggeredAbilityEffect() {
        super(Outcome.Benefit);
        this.staticText = "At this turn's next end of combat, destroy all creatures that blocked or were blocked by it this turn";
    }

    public VenomousBreathCreateDelayedTriggeredAbilityEffect(final VenomousBreathCreateDelayedTriggeredAbilityEffect effect) {
        super(effect);
    }

    @Override
    public VenomousBreathCreateDelayedTriggeredAbilityEffect copy() {
        return new VenomousBreathCreateDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty() && source.getFirstTarget() != null) {
            MageObjectReference mor = new MageObjectReference(source.getFirstTarget(), game);
            AtTheEndOfCombatDelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new VenomousBreathEffect(mor));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class VenomousBreathEffect extends OneShotEffect {
    
    MageObjectReference targetCreature;

    public VenomousBreathEffect(MageObjectReference targetCreature) {
        super(Outcome.DestroyPermanent);
        this.targetCreature = targetCreature;
    }

    public VenomousBreathEffect(final VenomousBreathEffect effect) {
        super(effect);
        targetCreature = effect.targetCreature;
    }

    @Override
    public VenomousBreathEffect copy() {
        return new VenomousBreathEffect(this);
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
                        if (watcher.creatureHasBlockedAttacker(new MageObjectReference(creature, game), targetCreature, game) || watcher.creatureHasBlockedAttacker(targetCreature, new MageObjectReference(creature, game), game)) {
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
