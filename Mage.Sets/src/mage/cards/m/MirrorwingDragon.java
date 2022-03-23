package mage.cards.m;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
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
public final class MirrorwingDragon extends CardImpl {

    public MirrorwingDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a player casts an instant or sorcery spell that targets only Mirrorwing Dragon,
        // that player copies that spell for each other creature they control that the spell could target.
        // Each copy targets a different one of those creatures.
        this.addAbility(new MirrorwingDragonCopyTriggeredAbility());
    }

    private MirrorwingDragon(final MirrorwingDragon card) {
        super(card);
    }

    @Override
    public MirrorwingDragon copy() {
        return new MirrorwingDragon(this);
    }
}

class MirrorwingDragonCopyTriggeredAbility extends TriggeredAbilityImpl {

    MirrorwingDragonCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MirrorwingDragonCopySpellEffect(), false);
    }

    private MirrorwingDragonCopyTriggeredAbility(final MirrorwingDragonCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MirrorwingDragonCopyTriggeredAbility copy() {
        return new MirrorwingDragonCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return checkSpell(spell, game);
    }

    private boolean checkSpell(Spell spell, Game game) {
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
        return "Whenever a player casts an instant or sorcery spell that targets only {this}, "
                + "that player copies that spell for each other creature they control that the spell could target. "
                + "Each copy targets a different one of those creatures.";
    }
}

class MirrorwingDragonCopySpellEffect extends CopySpellForEachItCouldTargetEffect {

    MirrorwingDragonCopySpellEffect() {
        super();
        this.staticText = "that player copies that spell for each other creature they control that the spell could target. Each copy targets a different one of those creatures.";
    }

    private MirrorwingDragonCopySpellEffect(MirrorwingDragonCopySpellEffect effect) {
        super(effect);
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        Spell spell = getStackObject(game, source);
        if (spell == null) {
            return null;
        }
        return game.getPlayer(spell.getControllerId());
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
    public MirrorwingDragonCopySpellEffect copy() {
        return new MirrorwingDragonCopySpellEffect(this);
    }
}
