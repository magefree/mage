package mage.cards.k;

import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.Filter;
import mage.filter.StaticFilters;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerBatchEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DroneToken;
import mage.players.Player;
import mage.target.TargetObject;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 *
 * @author @stwalsh4118
 */
public final class KaitoDancingShadow extends CardImpl {

    public KaitoDancingShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{B}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAITO);
        this.setStartingLoyalty(3);

        // Whenever one or more creatures you control deal combat damage to a player, you may return one of them to its owner's hand. If you do, you may activate loyalty abilities of Kaito twice this turn rather than only once.
        Ability ability = new KaitoDancingShadowTriggeredAbility();
        this.addAbility(ability);

        // +1: Up to one target creature can't attack or block until your next turn.
        Ability KaitoCantAttackOrBlockAbility = new LoyaltyAbility(new CantAttackTargetEffect(Duration.UntilYourNextTurn).setText("Up to one target creature can't attack "), 1);
        KaitoCantAttackOrBlockAbility.addEffect(new CantBlockTargetEffect(Duration.UntilYourNextTurn).setText("or block until your next turn"));
        KaitoCantAttackOrBlockAbility.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(KaitoCantAttackOrBlockAbility);

        // 0: Draw a card.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 0));

        // -2: Create a 2/2 colorless Drone artifact creature token with deathtouch and "When this creature leaves the battlefield, each opponent loses 2 life and you gain 2 life."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DroneToken()), -2));
    }

    private KaitoDancingShadow(final KaitoDancingShadow card) {
        super(card);
    }

    @Override
    public KaitoDancingShadow copy() {
        return new KaitoDancingShadow(this);
    }
}

class KaitoDancingShadowTriggeredAbility extends TriggeredAbilityImpl {

    KaitoDancingShadowTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KaitoDancingShadowEffect());
        this.setTriggerPhrase("Whenever one or more creatures you control deal combat damage to a player, ");
        this.addWatcher(new KaitoDancingShadowWatcher());

    }

    private KaitoDancingShadowTriggeredAbility(final KaitoDancingShadowTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerBatchEvent dEvent = (DamagedPlayerBatchEvent) event;
        for (DamagedEvent damagedEvent : dEvent.getEvents()) {
            if (!damagedEvent.isCombatDamage()) {
                continue;
            }
            Permanent permanent = game.getPermanent(damagedEvent.getSourceId());
            if (permanent != null && permanent.isControlledBy(getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public KaitoDancingShadowTriggeredAbility copy() {
        return new KaitoDancingShadowTriggeredAbility(this);
    }
}

class KaitoDancingShadowEffect extends OneShotEffect {

    KaitoDancingShadowEffect() {
        super(Outcome.Benefit);
        this.setText("you may return one of them to its owner's hand. If you do, you may activate loyalty abilities of Kaito twice this turn rather than only once");
    }

    private KaitoDancingShadowEffect(final KaitoDancingShadowEffect effect) {
        super(effect);
    }

    @Override
    public KaitoDancingShadowEffect copy() {
        return new KaitoDancingShadowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) { 
        KaitoDancingShadowWatcher watcher = game.getState().getWatcher(KaitoDancingShadowWatcher.class);
        if (watcher == null) {
            return false;
        }
        TargetCreatureThatDealtCombatDamage target = new TargetCreatureThatDealtCombatDamage(0, 1, watcher.getPermanents());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.chooseUse(outcome, "Return a creature card that dealt damage to hand?", source, game) && target.chooseTarget(Outcome.ReturnToHand, source.getControllerId(), source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);

                ContinuousEffectImpl effect = new KaitoDancingShadowIncreaseLoyaltyUseEffect();
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}

class KaitoDancingShadowWatcher extends Watcher {

    private final List<Permanent> permanents = new ArrayList<>();

    KaitoDancingShadowWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            permanents.clear();
            return;
        }
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER
                || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return;
        }
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature == null) {
            return;
        }
        permanents.add(creature);
    }

    public List<Permanent> getPermanents() {
        return permanents;
    }
}

class TargetCreatureThatDealtCombatDamage extends TargetObject {

    protected List<Permanent> permanents;
    private Permanent firstTarget = null;

    public TargetCreatureThatDealtCombatDamage() {
        super();
    }

    public TargetCreatureThatDealtCombatDamage(final TargetCreatureThatDealtCombatDamage target) {
        super(target);
        this.firstTarget = target.firstTarget;
    }


    public TargetCreatureThatDealtCombatDamage(int minNumTargets, int maxNumTargets, List<Permanent> permanents) {
        super(minNumTargets, maxNumTargets, Zone.BATTLEFIELD, true);
        this.permanents = permanents;
    }


    @Override
    public boolean canTarget(UUID id, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.BATTLEFIELD) {
            for (Permanent permanent : permanents) {
                if (permanent.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        firstTarget = game.getPermanent(source.getFirstTarget());
        return super.chooseTarget(Outcome.Benefit, playerId, source, game);
    }

    
    @Override
    public TargetCreatureThatDealtCombatDamage copy() {
        return new TargetCreatureThatDealtCombatDamage(this);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return permanents.size() > 0;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (Permanent permanent : permanents) {
            if (permanent != null && permanent.isControlledBy(sourceControllerId)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        // TODO Auto-generated method stub
        return null;
    }
}

class KaitoDancingShadowIncreaseLoyaltyUseEffect extends ContinuousEffectImpl {

    public KaitoDancingShadowIncreaseLoyaltyUseEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
    }

    public KaitoDancingShadowIncreaseLoyaltyUseEffect(final KaitoDancingShadowIncreaseLoyaltyUseEffect effect) {
        super(effect);
    }

    @Override
    public KaitoDancingShadowIncreaseLoyaltyUseEffect copy() {
        return new KaitoDancingShadowIncreaseLoyaltyUseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER,
            source.getControllerId(), source, game
        )) {
            permanent.setLoyaltyActivationsAvailable(2);
        }

        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

