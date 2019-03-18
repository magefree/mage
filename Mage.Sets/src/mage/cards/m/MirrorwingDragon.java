
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
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
public final class MirrorwingDragon extends CardImpl {

    public MirrorwingDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a player casts an instant or sorcery spell that targets only Mirrorwing Dragon,
        // that player copies that spell for each other creature he or she controls that the spell could target.
        // Each copy targets a different one of those creatures.
        this.addAbility(new MirrorwingDragonCopyTriggeredAbility());
    }

    public MirrorwingDragon(final MirrorwingDragon card) {
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

    MirrorwingDragonCopyTriggeredAbility(final MirrorwingDragonCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MirrorwingDragonCopyTriggeredAbility copy() {
        return new MirrorwingDragonCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return checkSpell(spell, game);
    }

    private boolean checkSpell(Spell spell, Game game) {
        if (spell != null
                && (spell.isInstant() || spell.isSorcery())) {
            boolean noTargets = true;
            for (TargetAddress addr : TargetAddress.walk(spell)) {
                noTargets = false;
                Target targetInstance = addr.getTarget(spell);
                for (UUID target : targetInstance.getTargets()) {
                    Permanent permanent = game.getPermanent(target);
                    if (permanent == null || !permanent.getId().equals(getSourceId())) {
                        return false;
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
        return "Whenever a player casts an instant or sorcery spell that targets only {this}, "
                + "that player copies that spell for each other creature he or she controls that the spell could target. "
                + "Each copy targets a different one of those creatures.";
    }
}

class MirrorwingDragonCopySpellEffect extends CopySpellForEachItCouldTargetEffect<Permanent> {

    public MirrorwingDragonCopySpellEffect() {
        this(new FilterControlledCreaturePermanent());
        this.staticText = "that player copies that spell for each other creature he or she controls that the spell could target. Each copy targets a different one of those creatures.";
    }

    public MirrorwingDragonCopySpellEffect(MirrorwingDragonCopySpellEffect effect) {
        super(effect);
    }

    private MirrorwingDragonCopySpellEffect(FilterInPlay<Permanent> filter) {
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
        if (permanent == null || !permanent.isControlledBy(spell.getControllerId())) {
            return false;
        }
        return true;
    }

    @Override
    public MirrorwingDragonCopySpellEffect copy() {
        return new MirrorwingDragonCopySpellEffect(this);
    }
}
