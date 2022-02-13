package mage.cards.f;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeatherTheRedeemed extends CardImpl {

    public FeatherTheRedeemed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell that targets a creature you control, 
        // exile that card instead of putting it into your graveyard as it resolves. 
        // If you do, return it to your hand at the beginning of the next end step.
        this.addAbility(new FeatherTheRedeemedTriggeredAbility());
    }

    private FeatherTheRedeemed(final FeatherTheRedeemed card) {
        super(card);
    }

    @Override
    public FeatherTheRedeemed copy() {
        return new FeatherTheRedeemed(this);
    }
}

class FeatherTheRedeemedTriggeredAbility extends TriggeredAbilityImpl {

    FeatherTheRedeemedTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private FeatherTheRedeemedTriggeredAbility(final FeatherTheRedeemedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FeatherTheRedeemedTriggeredAbility copy() {
        return new FeatherTheRedeemedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        SpellAbility sa = spell.getSpellAbility();
        for (UUID modeId : sa.getModes().getSelectedModes()) {
            Mode mode = sa.getModes().get(modeId);
            for (Target target : mode.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.isCreature(game)
                            && permanent.isControlledBy(getControllerId())) {
                        this.getEffects().clear();
                        this.addEffect(new FeatherTheRedeemedEffect(new MageObjectReference(spell, game)));
                        return true;
                    }
                }
            }
            for (Effect effect : mode.getEffects()) {
                for (UUID targetId : effect.getTargetPointer().getTargets(game, sa)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.isCreature(game)
                            && permanent.isControlledBy(getControllerId())) {
                        this.getEffects().clear();
                        this.addEffect(new FeatherTheRedeemedEffect(new MageObjectReference(spell, game)));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell that targets a creature you control, " +
                "exile that card instead of putting it into your graveyard as it resolves. " +
                "If you do, return it to your hand at the beginning of the next end step.";
    }
}

class FeatherTheRedeemedEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    FeatherTheRedeemedEffect(MageObjectReference mor) {
        super(Duration.WhileOnStack, Outcome.Benefit);
        this.mor = mor;
    }

    private FeatherTheRedeemedEffect(final FeatherTheRedeemedEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(event.getTargetId());
        if (sourceSpell == null || sourceSpell.isCopy()) {
            return false;
        }
        Player player = game.getPlayer(sourceSpell.getOwnerId());
        if (player == null) {
            return false;
        }
        Effect effect = new ReturnToHandTargetEffect().setText("return " + sourceSpell.getName() + " to its owner's hand");
        player.moveCards(sourceSpell, Zone.EXILED, source, game);
        effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        if (zEvent.getFromZone() != Zone.STACK
                || zEvent.getToZone() != Zone.GRAVEYARD
                || event.getSourceId() == null
                || !event.getSourceId().equals(event.getTargetId())
                || mor.getZoneChangeCounter() != game.getState().getZoneChangeCounter(event.getSourceId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(mor.getSourceId());
        return spell != null && spell.isInstantOrSorcery(game);
    }

    @Override
    public FeatherTheRedeemedEffect copy() {
        return new FeatherTheRedeemedEffect(this);
    }
}