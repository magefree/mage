package mage.cards.z;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.util.TargetAddress;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class ZadaHedronGrinder extends CardImpl {

    public ZadaHedronGrinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN, SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder,
        // copy that spell for each other creature you control that the spell could target.
        // Each copy targets a different one of those creatures.
        this.addAbility(new ZadaHedronGrinderTriggeredAbility());
    }

    private ZadaHedronGrinder(final ZadaHedronGrinder card) {
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

    private ZadaHedronGrinderTriggeredAbility(final ZadaHedronGrinderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZadaHedronGrinderTriggeredAbility copy() {
        return new ZadaHedronGrinderTriggeredAbility(this);
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
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        boolean noTargets = true;
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            if (addr == null) {
                continue;
            }
            noTargets = false;
            Target targetInstance = addr.getTarget(spell);
            if (targetInstance == null) {
                continue;
            }
            for (UUID target : targetInstance.getTargets()) {
                if (target == null) {
                    continue;
                }
                Permanent permanent = game.getPermanent(target);
                if (permanent == null || !permanent.getId().equals(getSourceId())) {
                    return false;
                }
            }
        }
        if (noTargets) {
            return false;
        }
        getEffects().setValue("triggeringSpell", spell);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell that targets only {this}, "
                + "copy that spell for each other creature you control that the spell could target. "
                + "Each copy targets a different one of those creatures.";
    }
}

class ZadaHedronGrinderCopySpellEffect extends CopySpellForEachItCouldTargetEffect {

    ZadaHedronGrinderCopySpellEffect() {
        super();
    }

    private ZadaHedronGrinderCopySpellEffect(final ZadaHedronGrinderCopySpellEffect effect) {
        super(effect);
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    protected List<MageObjectReferencePredicate> prepareCopiesWithTargets(StackObject stackObject, Player player, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        player.getId(), source, game
                ).stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.equals(permanent))
                .filter(p -> stackObject.canTarget(game, p.getId()))
                .map(p -> new MageObjectReference(p, game))
                .map(MageObjectReferencePredicate::new)
                .collect(Collectors.toList());
    }

    @Override
    protected Spell getStackObject(Game game, Ability source) {
        return (Spell) getValue("triggeringSpell");
    }

    @Override
    public ZadaHedronGrinderCopySpellEffect copy() {
        return new ZadaHedronGrinderCopySpellEffect(this);
    }
}
