package mage.cards.k;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public final class KaradorGhostChieftain extends CardImpl {

    public KaradorGhostChieftain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new KaradorGhostChieftainCostReductionEffect()));

        // During each of your turns, you may cast one creature card from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new KaradorGhostChieftainCastFromGraveyardEffect()).setIdentifier(MageIdentifier.KaradorGhostChieftainWatcher),
                new KaradorGhostChieftainWatcher());
    }

    private KaradorGhostChieftain(final KaradorGhostChieftain card) {
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
        staticText = "this spell costs {1} less to cast for each creature card in your graveyard";
    }

    KaradorGhostChieftainCostReductionEffect(KaradorGhostChieftainCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int reductionAmount = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
            CardUtil.reduceCost(abilityToModify, reductionAmount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility)
                && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public KaradorGhostChieftainCostReductionEffect copy() {
        return new KaradorGhostChieftainCostReductionEffect(this);
    }
}

class KaradorGhostChieftainCastFromGraveyardEffect extends AsThoughEffectImpl {

    KaradorGhostChieftainCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PutCreatureInPlay, true);
        staticText = "During each of your turns, you may cast a creature spell from your graveyard";
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
        if (source.isControlledBy(affectedControllerId)
                && Zone.GRAVEYARD.equals(game.getState().getZone(objectId))) {
            Card objectCard = game.getCard(objectId);
            Permanent sourceObject = game.getPermanent(source.getSourceId()); // needs to be onto the battlefield
            if (objectCard != null
                    && sourceObject != null
                    && objectCard.isOwnedBy(source.getControllerId())
                    && objectCard.isCreature(game)
                    && objectCard.getSpellAbility() != null
                    && objectCard.getSpellAbility().spellCanBeActivatedRegularlyNow(affectedControllerId, game)) {
                KaradorGhostChieftainWatcher watcher
                        = game.getState().getWatcher(KaradorGhostChieftainWatcher.class);
                return watcher != null
                        && !watcher.isAbilityUsed(new MageObjectReference(sourceObject, game));
            }
        }
        return false;
    }
}

class KaradorGhostChieftainWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    KaradorGhostChieftainWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.KaradorGhostChieftainWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean isAbilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}
