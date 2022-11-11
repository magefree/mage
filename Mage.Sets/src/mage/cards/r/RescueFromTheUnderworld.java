package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;
import mage.filter.StaticFilters;

/**
 * Once you announce you're casting Rescue from the Underworld, no player may
 * attempt to stop you from casting the spell by removing the creature you want
 * to sacrifice.
 * <p>
 * If you sacrifice a creature token to cast Rescue from the Underworld, it
 * won't return to the battlefield, although the target creature card will.
 * <p>
 * If either the sacrificed creature or the target creature card leaves the
 * graveyard before the delayed triggered ability resolves during your next
 * upkeep, it won't return.
 * <p>
 * However, if the sacrificed creature is put into another public zone instead
 * of the graveyard, perhaps because it's your commander or because of another
 * replacement effect, it will return to the battlefield from the zone it went
 * to.
 * <p>
 * Rescue from the Underworld is exiled as it resolves, not later as its delayed
 * trigger resolves.
 *
 * @author LevelX2
 */
public final class RescueFromTheUnderworld extends CardImpl {

    public RescueFromTheUnderworld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // As an additional cost to cast Rescue from the Underworld, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, false)));

        // Choose target creature card in your graveyard. Return that card and the sacrificed card to the battlefield under your control at the beginning of your next upkeep. Exile Rescue from the Underworld.
        this.getSpellAbility().addEffect(new RescueFromTheUnderworldTextEffect());
        this.getSpellAbility().addEffect(new RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect(new RescueFromTheUnderworldDelayedTriggeredAbility()));
        Target target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private RescueFromTheUnderworld(final RescueFromTheUnderworld card) {
        super(card);
    }

    @Override
    public RescueFromTheUnderworld copy() {
        return new RescueFromTheUnderworld(this);
    }
}

class RescueFromTheUnderworldTextEffect extends OneShotEffect {

    public RescueFromTheUnderworldTextEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose target creature card in your graveyard";
    }

    public RescueFromTheUnderworldTextEffect(final RescueFromTheUnderworldTextEffect effect) {
        super(effect);
    }

    @Override
    public RescueFromTheUnderworldTextEffect copy() {
        return new RescueFromTheUnderworldTextEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

class RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect extends OneShotEffect {

    protected DelayedTriggeredAbility ability;

    public RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability) {
        super(ability.getEffects().getOutcome(ability));
        this.ability = ability;
        this.staticText = "Return that card and the sacrificed card to the battlefield under your control at the beginning of your next upkeep";
    }

    public RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect(final RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect copy() {
        return new RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = ability.copy();
        delayedAbility.getTargets().addAll(source.getTargets());
        for (Effect effect : delayedAbility.getEffects()) {
            effect.getTargetPointer().init(game, source);
        }
        // add the sacrificed creature as target
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacCost = (SacrificeTargetCost) cost;
                TargetCardInGraveyard target = new TargetCardInGraveyard();
                for (Permanent permanent : sacCost.getPermanents()) {
                    target.add(permanent.getId(), game);
                    delayedAbility.getTargets().add(target);
                }
            }
        }

        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class RescueFromTheUnderworldDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public RescueFromTheUnderworldDelayedTriggeredAbility() {
        this(new RescueFromTheUnderworldReturnEffect(), TargetController.YOU);
    }

    public RescueFromTheUnderworldDelayedTriggeredAbility(Effect effect, TargetController targetController) {
        super(effect);
    }

    public RescueFromTheUnderworldDelayedTriggeredAbility(RescueFromTheUnderworldDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public RescueFromTheUnderworldDelayedTriggeredAbility copy() {
        return new RescueFromTheUnderworldDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Return that card and the sacrificed card to the battlefield under your control at the beginning of your next upkeep";
    }
}

class RescueFromTheUnderworldReturnEffect extends OneShotEffect {

    public RescueFromTheUnderworldReturnEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public RescueFromTheUnderworldReturnEffect(final RescueFromTheUnderworldReturnEffect effect) {
        super(effect);
    }

    @Override
    public RescueFromTheUnderworldReturnEffect copy() {
        return new RescueFromTheUnderworldReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Target card comes only back if in graveyard
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            // However, if the sacrificed creature is put into another public zone instead of the graveyard,
            // perhaps because it's your commander or because of another replacement effect, it will return
            // to the battlefield from the zone it went to.
            if (source.getTargets().get(1) != null) {
                for (UUID targetId : source.getTargets().get(1).getTargets()) {
                    Card card = game.getCard(targetId);
                    if (card != null && !card.isFaceDown(game)) {
                        Player player = game.getPlayer(card.getOwnerId());
                        if (player != null) {
                            Zone currentZone = game.getState().getZone(card.getId());
                            if (currentZone == Zone.COMMAND || currentZone == Zone.GRAVEYARD || currentZone == Zone.EXILED) {
                                return player.moveCards(card, Zone.BATTLEFIELD, source, game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }

}
