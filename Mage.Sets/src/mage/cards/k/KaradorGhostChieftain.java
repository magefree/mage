
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public final class KaradorGhostChieftain extends CardImpl {

    public KaradorGhostChieftain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new KaradorGhostChieftainCostReductionEffect()));

        // During each of your turns, you may cast one creature card from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KaradorGhostChieftainContinuousEffect()), new KaradorGhostChieftainWatcher());
    }

    public KaradorGhostChieftain(final KaradorGhostChieftain card) {
        super(card);
    }

    @Override
    public KaradorGhostChieftain copy() {
        return new KaradorGhostChieftain(this);
    }
}

class KaradorGhostChieftainCostReductionEffect extends CostModificationEffectImpl {

    KaradorGhostChieftainCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {1} less to cast for each creature card in your graveyard";
    }

    KaradorGhostChieftainCostReductionEffect(KaradorGhostChieftainCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int reductionAmount = player.getGraveyard().count(new FilterCreatureCard(), game);
            CardUtil.reduceCost(abilityToModify, reductionAmount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public KaradorGhostChieftainCostReductionEffect copy() {
        return new KaradorGhostChieftainCostReductionEffect(this);
    }
}

class KaradorGhostChieftainContinuousEffect extends ContinuousEffectImpl {

    KaradorGhostChieftainContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "During each of your turns, you may cast one creature card from your graveyard";
    }

    KaradorGhostChieftainContinuousEffect(final KaradorGhostChieftainContinuousEffect effect) {
        super(effect);
    }

    @Override
    public KaradorGhostChieftainContinuousEffect copy() {
        return new KaradorGhostChieftainContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (game.getActivePlayerId() == null || !game.isActivePlayer(player.getId())) {
                return false;
            }
            for (Card card : player.getGraveyard().getCards(new FilterCreatureCard(), game)) {
                ContinuousEffect effect = new KaradorGhostChieftainCastFromGraveyardEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class KaradorGhostChieftainCastFromGraveyardEffect extends AsThoughEffectImpl {

    KaradorGhostChieftainCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast one creature card from your graveyard";
    }

    KaradorGhostChieftainCastFromGraveyardEffect(final KaradorGhostChieftainCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KaradorGhostChieftainCastFromGraveyardEffect copy() {
        return new KaradorGhostChieftainCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                KaradorGhostChieftainWatcher watcher = (KaradorGhostChieftainWatcher) game.getState().getWatchers().get(KaradorGhostChieftainWatcher.class.getSimpleName(), source.getSourceId());
                return !watcher.isAbilityUsed();
            }
        }
        return false;
    }
}

class KaradorGhostChieftainWatcher extends Watcher {

    boolean abilityUsed = false;

    KaradorGhostChieftainWatcher() {
        super(KaradorGhostChieftainWatcher.class.getSimpleName(), WatcherScope.CARD);
    }

    KaradorGhostChieftainWatcher(final KaradorGhostChieftainWatcher watcher) {
        super(watcher);
        this.abilityUsed = watcher.abilityUsed;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.GRAVEYARD) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.isCreature()) {
                abilityUsed = true;
            }
        }
    }

    @Override
    public KaradorGhostChieftainWatcher copy() {
        return new KaradorGhostChieftainWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        abilityUsed = false;
    }

    public boolean isAbilityUsed() {
        return abilityUsed;
    }
}
