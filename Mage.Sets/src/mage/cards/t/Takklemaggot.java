package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CanBeEnchantedByPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Takklemaggot extends CardImpl {

    public Takklemaggot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of the upkeep of enchanted creature's controller, put a -0/-1 counter on that creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersAttachedEffect(CounterType.M0M1.createInstance(), "that creature"),
                TargetController.CONTROLLER_ATTACHED_TO, false, false));

        // When enchanted creature dies, that creature's controller chooses a creature that Takklemaggot could enchant.
        // If they do, return Takklemaggot to the battlefield under your control attached to that creature.
        // If they don't, return Takklemaggot to the battlefield under your control as a non-Aura enchantment.
        // It loses "enchant creature" and gains "At the beginning of that player's upkeep, Takklemaggot deals 1 damage to that player."
        this.addAbility(new DiesAttachedTriggeredAbility(new TakklemaggotEffect(), "enchanted creature",
                false, true, SetTargetPointer.ATTACHED_TO_CONTROLLER));
    }

    private Takklemaggot(final Takklemaggot card) {
        super(card);
    }

    @Override
    public Takklemaggot copy() {
        return new Takklemaggot(this);
    }

}

class TakklemaggotEffect extends OneShotEffect {

    TakklemaggotEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "that creature's controller chooses a creature that {this} could enchant. " +
                "If the player does, return {this} to the battlefield under your control attached to that creature. " +
                "If they don't, return {this} to the battlefield under your control as a non-Aura enchantment. " +
                "It loses \"enchant creature\" and gains \"At the beginning of that player's upkeep, {this} deals 1 damage to that player.\"";
    }

    private TakklemaggotEffect(final TakklemaggotEffect effect) {
        super(effect);
    }

    @Override
    public TakklemaggotEffect copy() {
        return new TakklemaggotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Card auraCard = game.getCard(source.getSourceId());
        if (controller == null || player == null || auraCard == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new CanBeEnchantedByPredicate(auraCard));
        Target target = new TargetCreaturePermanent(filter).withNotTarget(true);
        if (!game.getBattlefield().getActivePermanents(filter, player.getId(), source, game).isEmpty()
                && player.choose(outcome, target, source, game)) {
            // return attached to that creature
            Permanent creature = game.getPermanent(target.getFirstTarget());
            if (creature == null) {
                return false;
            }
            game.getState().setValue("attachTo:" + auraCard.getId(), creature);
            controller.moveCards(auraCard, Zone.BATTLEFIELD, source, game);
            creature.addAttachment(auraCard.getId(), source, game);
        } else {
            // return as non-Aura enchantment
            game.addEffect(new TakklemaggotNonAuraEffect(player.getId()), source);
            controller.moveCards(auraCard, Zone.BATTLEFIELD, source, game);
            auraCard.addInfo("chosen player", CardUtil.addToolTipMarkTags("Chosen player: " + player.getLogName()), game);
        }
        return true;
    }

}

class TakklemaggotNonAuraEffect extends ContinuousEffectImpl {

    private final UUID playerId;

    TakklemaggotNonAuraEffect(UUID playerId) {
        super(Duration.Custom, Outcome.AddAbility);
        this.playerId = playerId;
    }

    private TakklemaggotNonAuraEffect(final TakklemaggotNonAuraEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
    }

    @Override
    public TakklemaggotNonAuraEffect copy() {
        return new TakklemaggotNonAuraEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent;
        if (affectedObjectList.isEmpty()) { // not yet initiated, check entering permanent
            permanent = game.getPermanentEntering(source.getSourceId());
            // if an entering permanent is found, then proceed to applying continuous effects by layer
            // otherwise try to find the permanent on the battlefield
            if (permanent == null) {
                permanent = game.getPermanent(source.getSourceId());
                if (permanent == null) {
                    discard(); // no permanent found, can't initiate
                    return false;
                } else {
                    // initiate with ZCC and check that in all future calls
                    affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
                }
            }
        } else {
            permanent = affectedObjectList.get(0).getPermanent(game);
            if (permanent == null) {
                discard(); // permanent no longer on battlefield so effect no longer applies
                return true;
            }
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeSubType(game, SubType.AURA);
                return true;
            case AbilityAddingRemovingEffects_6:
                List<Ability> toRemove = new ArrayList<>();
                for (Ability ability : permanent.getAbilities(game)) {
                    if (ability instanceof EnchantAbility) {
                        toRemove.add(ability);
                    }
                }
                permanent.removeAbilities(toRemove, source.getSourceId(), game);
                permanent.addAbility(new TakklemaggotUpkeepAbility(playerId), source.getSourceId(), game);
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return Layer.AbilityAddingRemovingEffects_6 == layer || Layer.TypeChangingEffects_4 == layer;
    }

}

class TakklemaggotUpkeepAbility extends TriggeredAbilityImpl {

    private final UUID playerId;

    TakklemaggotUpkeepAbility(UUID playerId) {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1, true, "that player")
                .setTargetPointer(new FixedTarget(playerId)), false);
        this.playerId = playerId;
        setTriggerPhrase("At the beginning of that player's upkeep, ");
    }

    private TakklemaggotUpkeepAbility(final TakklemaggotUpkeepAbility ability) {
        super(ability);
        this.playerId = ability.playerId;
    }

    @Override
    public TakklemaggotUpkeepAbility copy() {
        return new TakklemaggotUpkeepAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.playerId);
    }

}
