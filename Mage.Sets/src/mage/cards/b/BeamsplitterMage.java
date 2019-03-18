package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeamsplitterMage extends CardImpl {

    public BeamsplitterMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell that targets only Beamsplitter Mage, if you control one or more creatures that spell could target, choose one of those creatures. Copy that spell. The copy targets the chosen creature.
        this.addAbility(new BeamsplitterMageTriggeredAbility());
    }

    public BeamsplitterMage(final BeamsplitterMage card) {
        super(card);
    }

    @Override
    public BeamsplitterMage copy() {
        return new BeamsplitterMage(this);
    }
}

class BeamsplitterMageTriggeredAbility extends TriggeredAbilityImpl {

    public BeamsplitterMageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BeamsplitterMageEffect(), false);
    }

    public BeamsplitterMageTriggeredAbility(final BeamsplitterMageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeamsplitterMageTriggeredAbility copy() {
        return new BeamsplitterMageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (!isControlledInstantOrSorcery(spell)) {
                return false;
            }
            boolean targetsSource = false;
            for (Ability ability : spell.getSpellAbilities()) {
                for (UUID modeId : ability.getModes().getSelectedModes()) {
                    Mode mode = ability.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        if (!target.isNotTarget()) {
                            for (UUID targetId : target.getTargets()) {
                                if (targetId.equals(getSourceId())) {
                                    targetsSource = true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            if (targetsSource) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(spell.getId()));
                return true;
            }
        }
        return false;
    }

    private boolean isControlledInstantOrSorcery(Spell spell) {
        return spell != null
                && (spell.isControlledBy(this.getControllerId()))
                && (spell.isInstant() || spell.isSorcery());
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell that targets "
                + "only {this}, if you control one or more creatures "
                + "that spell could target, choose one of those creatures. "
                + "Copy that spell. The copy targets the chosen creature.";
    }
}

class BeamsplitterMageEffect extends OneShotEffect {

    public BeamsplitterMageEffect() {
        super(Outcome.Detriment);
    }

    public BeamsplitterMageEffect(final BeamsplitterMageEffect effect) {
        super(effect);
    }

    @Override
    public BeamsplitterMageEffect copy() {
        return new BeamsplitterMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (spell != null && controller != null) {
            // search the target that targets source
            Target usedTarget = null;
            setUsedTarget:
            for (Ability ability : spell.getSpellAbilities()) {
                for (UUID modeId : ability.getModes().getSelectedModes()) {
                    Mode mode = ability.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        if (!target.isNotTarget() && target.getFirstTarget().equals(source.getSourceId())) {
                            usedTarget = target.copy();
                            usedTarget.clearChosen();
                            break setUsedTarget;
                        }
                    }
                }
            }
            if (usedTarget == null) {
                return false;
            }
            FilterPermanent filter = new BeamsplitterMageFilter(usedTarget, source.getSourceId(), source.getControllerId());
            Target target1 = new TargetPermanent(filter);
            target1.setNotTarget(true);
            if (controller.choose(outcome, target1, source.getSourceId(), game)) {
                Permanent creature = game.getPermanent(target1.getFirstTarget());
                if (creature == null) {
                    return false;
                }
                Spell copy = spell.copySpell(source.getControllerId());
                game.getStack().push(copy);
                setTarget:
                for (UUID modeId : copy.getSpellAbility().getModes().getSelectedModes()) {
                    Mode mode = copy.getSpellAbility().getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        if (target.getClass().equals(usedTarget.getClass())) {
                            target.clearChosen(); // For targets with Max > 1 we need to clear before the text is comapred
                            if (target.getMessage().equals(usedTarget.getMessage())) {
                                target.addTarget(creature.getId(), copy.getSpellAbility(), game, false);
                                break setTarget;
                            }
                        }
                    }
                }
                game.fireEvent(new GameEvent(GameEvent.EventType.COPIED_STACKOBJECT, copy.getId(), spell.getId(), source.getControllerId()));
                String activateMessage = copy.getActivatedMessage(game);
                if (activateMessage.startsWith(" casts ")) {
                    activateMessage = activateMessage.substring(6);
                }
                if (!game.isSimulation()) {
                    game.informPlayers(controller.getLogName() + activateMessage);
                }
            }
            return true;
        }
        return false;
    }
}

class BeamsplitterMageFilter extends FilterControlledPermanent {

    private final Target target;
    private final UUID notId;

    public BeamsplitterMageFilter(Target target, UUID notId, UUID controllerId) {
        super("creature this spell could target");
        this.target = target;
        this.notId = notId;
        this.add(new ControllerIdPredicate(controllerId));
    }

    public BeamsplitterMageFilter(final BeamsplitterMageFilter filter) {
        super(filter);
        this.target = filter.target;
        this.notId = filter.notId;
    }

    @Override
    public BeamsplitterMageFilter copy() {
        return new BeamsplitterMageFilter(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        return super.match(permanent, game)
                && permanent.isCreature()
                && !permanent.getId().equals(notId)
                && target.canTarget(permanent.getId(), game);
    }
}
