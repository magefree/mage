package mage.cards.p;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GolemToken;
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
public final class PrecursorGolem extends CardImpl {

    public PrecursorGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Precursor Golem enters the battlefield, create two 3/3 colorless Golem artifact creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GolemToken(), 2), false));

        // Whenever a player casts an instant or sorcery spell that targets only a single Golem, that player copies that spell for each other Golem that spell could target. Each copy targets a different one of those Golems.
        this.addAbility(new PrecursorGolemCopyTriggeredAbility());
    }

    private PrecursorGolem(final PrecursorGolem card) {
        super(card);
    }

    @Override
    public PrecursorGolem copy() {
        return new PrecursorGolem(this);
    }
}

class PrecursorGolemCopyTriggeredAbility extends TriggeredAbilityImpl {

    PrecursorGolemCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PrecursorGolemCopySpellEffect(), false);
    }

    private PrecursorGolemCopyTriggeredAbility(final PrecursorGolemCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PrecursorGolemCopyTriggeredAbility copy() {
        return new PrecursorGolemCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        UUID targetGolem = null;
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID target : targetInstance.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent == null || !permanent.hasSubtype(SubType.GOLEM, game)) {
                    return false;
                }
                if (targetGolem == null) {
                    targetGolem = target;
                } else // If a spell has multiple targets, but it's targeting the same Golem with all of them, Precursor Golem's last ability will trigger
                {
                    if (!targetGolem.equals(target)) {
                        return false;
                    }
                }
            }
        }
        if (targetGolem == null) {
            return false;
        }
        getEffects().setValue("triggeringSpell", spell);
        getEffects().setValue("targetedGolem", targetGolem);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell that targets only a single Golem, " +
                "that player copies that spell for each other Golem that spell could target. " +
                "Each copy targets a different one of those Golems.";
    }
}

class PrecursorGolemCopySpellEffect extends CopySpellForEachItCouldTargetEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GOLEM, "");

    PrecursorGolemCopySpellEffect() {
        super();
    }

    private PrecursorGolemCopySpellEffect(PrecursorGolemCopySpellEffect effect) {
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
        Permanent permanent = game.getPermanent((UUID) getValue("targetedGolem"));
        return game.getBattlefield()
                .getActivePermanents(
                        filter, player.getId(), source, game
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
    public PrecursorGolemCopySpellEffect copy() {
        return new PrecursorGolemCopySpellEffect(this);
    }
}
