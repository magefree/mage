package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterInPlay;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.util.TargetAddress;

/**
 *
 * @author LevelX2
 */
public final class ZadaHedronGrinder extends CardImpl {

    public ZadaHedronGrinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN, SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, 
        // copy that spell for each other creature you control that the spell could target. 
        // Each copy targets a different one of those creatures.
        this.addAbility(new ZadaHedronGrinderTriggeredAbility());

    }

    public ZadaHedronGrinder(final ZadaHedronGrinder card) {
        super(card);
    }

    @Override
    public ZadaHedronGrinder copy() {
        return new ZadaHedronGrinder(this);
    }
}

class ZadaHedronGrinderTriggeredAbility extends TriggeredAbilityImpl {

    ZadaHedronGrinderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ZadaHedronGrinderCopySpellEffect(), false);
    }

    ZadaHedronGrinderTriggeredAbility(final ZadaHedronGrinderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZadaHedronGrinderTriggeredAbility copy() {
        return new ZadaHedronGrinderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return checkSpell(spell, game)
                && event.getPlayerId().equals(controllerId);
    }

    private boolean checkSpell(Spell spell, Game game) {
        if (spell != null
                && (spell.isInstant() 
                || spell.isSorcery())) {
            boolean noTargets = true;
            for (TargetAddress addr : TargetAddress.walk(spell)) {
                if (addr != null) {
                    noTargets = false;
                    Target targetInstance = addr.getTarget(spell);
                    if (targetInstance != null) {
                        for (UUID target : targetInstance.getTargets()) {
                            if (target != null) {
                                Permanent permanent = game.getPermanent(target);
                                if (permanent == null
                                        || !permanent.getId().equals(getSourceId())) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            if (noTargets) {
                return false;
            }
            getEffects().get(0).setValue("triggeringSpell", spell);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell that targets only {this}, "
                + "copy that spell for each other creature you control that the spell could target. "
                + "Each copy targets a different one of those creatures.";
    }
}

class ZadaHedronGrinderCopySpellEffect extends CopySpellForEachItCouldTargetEffect<Permanent> {

    public ZadaHedronGrinderCopySpellEffect() {
        this(new FilterControlledCreaturePermanent());
        this.staticText = "copy that spell for each other creature you control "
                + "that the spell could target. Each copy targets a different one of those creatures.";
    }

    public ZadaHedronGrinderCopySpellEffect(ZadaHedronGrinderCopySpellEffect effect) {
        super(effect);
    }

    private ZadaHedronGrinderCopySpellEffect(FilterInPlay<Permanent> filter) {
        super(filter);
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        Spell spell = getSpell(game, source);
        if (spell != null) {
            return game.getPlayer(spell.getControllerId());
        }
        return null;
    }

    @Override
    protected Spell getSpell(Game game, Ability source) {
        return (Spell) getValue("triggeringSpell");
    }

    @Override
    protected boolean changeTarget(Target target, Game game, Ability source) {
        return true;
    }

    @Override
    protected void modifyCopy(Spell copy, Game game, Ability source) {
        Spell spell = getSpell(game, source);
        copy.setControllerId(spell.getControllerId());
    }

    @Override
    protected boolean okUUIDToCopyFor(UUID potentialTarget, Game game, Ability source, Spell spell) {
        Permanent permanent = game.getPermanent(potentialTarget);
        if (permanent == null
                || !permanent.isControlledBy(spell.getControllerId())) {
            return false;
        }
        return true;
    }

    @Override
    public ZadaHedronGrinderCopySpellEffect copy() {
        return new ZadaHedronGrinderCopySpellEffect(this);
    }
}
