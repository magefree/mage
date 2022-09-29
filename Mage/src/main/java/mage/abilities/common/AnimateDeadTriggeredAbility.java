package mage.abilities.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2, awjackson
 */
public class AnimateDeadTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    public AnimateDeadTriggeredAbility() {
        this(false);
    }

    public AnimateDeadTriggeredAbility(boolean becomesAura) {
        this(becomesAura, false);
    }

    public AnimateDeadTriggeredAbility(boolean becomesAura, boolean tapped) {
        super(new AnimateDeadReplaceAbilityEffect(becomesAura));
        addEffect(new AnimateDeadPutOntoBattlefieldEffect(becomesAura, tapped));
        addWatcher(new AnimateDeadWatcher());
        setTriggerPhrase("When {this} enters the battlefield, if it's on the battlefield, ");
    }

    private AnimateDeadTriggeredAbility(final AnimateDeadTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnimateDeadTriggeredAbility copy() {
        return new AnimateDeadTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return getSourcePermanentIfItStillExists(game) != null;
    }
}

class AnimateDeadReplaceAbilityEffect extends ContinuousEffectImpl implements SourceEffect {

    private final boolean becomesAura;
    private Ability newAbility;
    private TargetCreaturePermanent newTarget;

    public AnimateDeadReplaceAbilityEffect(boolean becomesAura) {
        super(Duration.Custom, Outcome.AddAbility);
        this.becomesAura = becomesAura;
        staticText = (becomesAura ? "it becomes an Aura with" :
            "it loses \"enchant creature card in a graveyard\" and gains"
        ) + " \"enchant creature put onto the battlefield with {this}.\"";
        if (becomesAura) {
            dependencyTypes.add(DependencyType.AuraAddingRemoving);
        }
    }

    private AnimateDeadReplaceAbilityEffect(final AnimateDeadReplaceAbilityEffect effect) {
        super(effect);
        this.becomesAura = effect.becomesAura;
        this.newAbility = effect.newAbility;
        this.newTarget = effect.newTarget;
    }

    @Override
    public AnimateDeadReplaceAbilityEffect copy() {
        return new AnimateDeadReplaceAbilityEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));

        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature put onto the battlefield with {this}");
        filter.add(new AnimateDeadPredicate(source.getSourceId()));
        newTarget = new TargetCreaturePermanent(filter);
        newAbility = new EnchantAbility(newTarget);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            discard();
            return true;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                if (becomesAura) {
                    permanent.addSubType(game, SubType.AURA);
                }
                break;
            case AbilityAddingRemovingEffects_6:
                if (!becomesAura) {
                    List<Ability> toRemove = new ArrayList<>();
                    for (Ability ability : permanent.getAbilities(game)) {
                        if (ability instanceof EnchantAbility &&
                                ability.getRule().equals("Enchant creature card in a graveyard")) {
                            toRemove.add(ability);
                        }
                    }
                    permanent.removeAbilities(toRemove, source.getSourceId(), game);
                }
                permanent.addAbility(newAbility, source.getSourceId(), game);
                permanent.getSpellAbility().getTargets().clear();
                permanent.getSpellAbility().getTargets().add(newTarget);
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return Layer.AbilityAddingRemovingEffects_6 == layer || becomesAura && Layer.TypeChangingEffects_4 == layer;
    }
}

class AnimateDeadPutOntoBattlefieldEffect extends OneShotEffect {

    private final boolean becomesAura;
    private final boolean tapped;

    public AnimateDeadPutOntoBattlefieldEffect(boolean becomesAura, boolean tapped) {
        super(Outcome.PutCreatureInPlay);
        this.becomesAura = becomesAura;
        this.tapped = tapped;
        StringBuilder sb = new StringBuilder(becomesAura || tapped ? "put " : "return ");
        sb.append(becomesAura ? "target creature card from a graveyard" : "enchanted creature card");
        sb.append(becomesAura || tapped ? " onto" : " to");
        sb.append(" the battlefield ");
        if (tapped) {
            sb.append("tapped ");
        }
        sb.append("under your control and attach {this} to it. When {this} leaves the battlefield, that creature's controller sacrifices it");
        staticText = sb.toString();
    }

    private AnimateDeadPutOntoBattlefieldEffect(final AnimateDeadPutOntoBattlefieldEffect effect) {
        super(effect);
        this.becomesAura = effect.becomesAura;
        this.tapped = effect.tapped;
    }

    @Override
    public AnimateDeadPutOntoBattlefieldEffect copy() {
        return new AnimateDeadPutOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (player == null || enchantment == null) {
            return false;
        }
        Card card = game.getCard(becomesAura ? getTargetPointer().getFirst(game, source) : enchantment.getAttachedTo());
        // If this ability was copied or made to trigger an additional time, the card might no longer be in the graveyard
        // See https://github.com/magefree/mage/issues/8253
        if (card == null || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return true;
        }
        // Put card onto the battlefield under your control...
        player.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, false, null);
        game.getState().processAction(game);

        Permanent creature = game.getPermanent(CardUtil.getDefaultCardSideForBattlefield(game, card).getId());
        if (creature == null) {
            return true;
        }
        // ...and attach {this} to it
        creature.addAttachment(enchantment.getId(), source, game);
        // When {this} leaves the battlefield, that creature's controller sacrifices it
        game.addDelayedTriggeredAbility(new AnimateDeadDelayedTriggeredAbility(new FixedTarget(creature, game)), source);
        return true;
    }
}

class AnimateDeadDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public AnimateDeadDelayedTriggeredAbility(FixedTarget fixedTarget) {
        super(new SacrificeTargetEffect("that creature's controller sacrifices it"));
        setTriggerPhrase("When {this} leaves the battlefield, ");
        getEffects().setTargetPointer(fixedTarget);
    }

    private AnimateDeadDelayedTriggeredAbility(final AnimateDeadDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnimateDeadDelayedTriggeredAbility copy() {
        return new AnimateDeadDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return getSourceId().equals(event.getTargetId()) && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD;
    }
}

class AnimateDeadPredicate implements Predicate<Permanent> {

    private final UUID sourceId;

    public AnimateDeadPredicate(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        AnimateDeadWatcher watcher = game.getState().getWatcher(AnimateDeadWatcher.class, sourceId);
        return watcher != null && watcher.check(input);
    }

    @Override
    public String toString() {
        return "AnimateDeadPredicate(" + sourceId + ')';
    }
}

class AnimateDeadWatcher extends Watcher {

    private final Set<UUID> putBySource = new HashSet<>();

    public AnimateDeadWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (sourceId.equals(zEvent.getTargetId())) {
                    // clear all data when source enchantment changes zones
                    putBySource.clear();
                    return;
                }
                if (zEvent.getToZone() != Zone.BATTLEFIELD) {
                    return;
                }
                if (sourceId.equals(zEvent.getSourceId())) {
                    putBySource.add(event.getTargetId());
                } else {
                    putBySource.remove(event.getTargetId());
                }
                return;
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    putBySource.clear();
                }
        }
    }

    public boolean check(Permanent permanent) {
        return putBySource.contains(permanent.getId());
    }
}
