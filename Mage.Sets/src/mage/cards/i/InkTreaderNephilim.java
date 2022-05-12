package mage.cards.i;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
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
 * @author duncant
 */
public final class InkTreaderNephilim extends CardImpl {

    public InkTreaderNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}{U}");
        this.subtype.add(SubType.NEPHILIM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player casts an instant or sorcery spell, if that spell targets only Ink-Treader Nephilim, copy the spell for each other creature that spell could target. Each copy targets a different one of those creatures.
        this.addAbility(new InkTreaderNephilimTriggeredAbility());
    }

    private InkTreaderNephilim(final InkTreaderNephilim card) {
        super(card);
    }

    @Override
    public InkTreaderNephilim copy() {
        return new InkTreaderNephilim(this);
    }
}

class InkTreaderNephilimTriggeredAbility extends TriggeredAbilityImpl {

    InkTreaderNephilimTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InkTreaderNephilimEffect(), false);
    }

    private InkTreaderNephilimTriggeredAbility(final InkTreaderNephilimTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InkTreaderNephilimTriggeredAbility copy() {
        return new InkTreaderNephilimTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null && spell.isInstantOrSorcery(game)) {
            getEffects().setValue("triggeringSpell", spell);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Spell spell = (Spell) getEffects().get(0).getValue("triggeringSpell");
        if (spell == null) {
            return false;
        }
        boolean flag = false;
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID target : targetInstance.getTargets()) {
                if (target == null) {
                    continue;
                }
                Permanent permanent = game.getPermanent(target);
                if (permanent == null || !permanent.getId().equals(getSourceId())) {
                    return false;
                }
                if (getSourceObjectZoneChangeCounter() != 0
                        && getSourceObjectZoneChangeCounter() != permanent.getZoneChangeCounter(game)) {
                    return false;
                }
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell, " +
                "if that spell targets only {this}, copy the spell for each other creature that spell could target. " +
                "Each copy targets a different one of those creatures.";
    }
}

class InkTreaderNephilimEffect extends CopySpellForEachItCouldTargetEffect {

    InkTreaderNephilimEffect() {
        super();
    }

    private InkTreaderNephilimEffect(InkTreaderNephilimEffect effect) {
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
                        StaticFilters.FILTER_PERMANENT_CREATURE,
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
    public InkTreaderNephilimEffect copy() {
        return new InkTreaderNephilimEffect(this);
    }
}
