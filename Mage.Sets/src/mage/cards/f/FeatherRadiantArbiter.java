package mage.cards.f;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.TargetAddress;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author brahle
 */
public final class FeatherRadiantArbiter extends CardImpl {

    public FeatherRadiantArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you cast a noncreature spell that targets only Feather, Radiant Arbiter,
        // you may choose any number of other creatures that spell could target and pay {2} for each of those creatures.
        // If you do, for each of those creatures, copy that spell. The copy targets that creature.
        // (Copies of permanent spells become tokens.)
        this.addAbility(new FeatherRadiantArbiterTriggeredAbility());
    }

    private FeatherRadiantArbiter(final FeatherRadiantArbiter card) {
        super(card);
    }

    @Override
    public FeatherRadiantArbiter copy() {
        return new FeatherRadiantArbiter(this);
    }
}

class FeatherRadiantArbiterTriggeredAbility extends TriggeredAbilityImpl {

    FeatherRadiantArbiterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FeatherRadiantArbiterEffect(), false);
    }

    private FeatherRadiantArbiterTriggeredAbility(final FeatherRadiantArbiterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FeatherRadiantArbiterTriggeredAbility copy() {
        return new FeatherRadiantArbiterTriggeredAbility(this);
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
        if (spell == null || spell.isCreature(game)) {
            return false;
        }
        boolean hasTarget = false;
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            if (targetInstance == null) {
                continue;
            }
            for (UUID target : targetInstance.getTargets()) {
                if (target == null) {
                    continue;
                }
                hasTarget = true;
                if (!target.equals(getSourceId())) {
                    return false;
                }
            }
        }
        if (!hasTarget) {
            return false;
        }
        getEffects().setValue("triggeringSpell", spell);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a noncreature spell that targets only {this}, you may choose any number of other creatures that spell could target and pay {2} for each of those creatures. "
                + "If you do, for each of those creatures, copy that spell. The copy targets that creature. "
                + "<i>(Copies of permanent spells become tokens.)</i>";
    }
}

class FeatherRadiantArbiterEffect extends CopySpellForEachItCouldTargetEffect {

    FeatherRadiantArbiterEffect() {
        super();
        staticText = "you may choose any number of other creatures that spell could target and pay {2} for each of those creatures. "
                + "If you do, for each of those creatures, copy that spell. The copy targets that creature. "
                + "<i>(Copies of permanent spells become tokens.)</i>";
    }

    private FeatherRadiantArbiterEffect(final FeatherRadiantArbiterEffect effect) {
        super(effect);
    }

    @Override
    public FeatherRadiantArbiterEffect copy() {
        return new FeatherRadiantArbiterEffect(this);
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    protected Spell getStackObject(Game game, Ability source) {
        return (Spell) getValue("triggeringSpell");
    }

    @Override
    protected List<MageObjectReferencePredicate> prepareCopiesWithTargets(StackObject stackObject, Player player, Ability source, Game game) {
        Spell spell = (Spell) stackObject;
        Permanent feather = source.getSourcePermanentOrLKI(game);
        UUID featherId = feather != null ? feather.getId() : source.getSourceId();

        List<Permanent> eligibleCreatures = game.getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)
                .stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.getId().equals(featherId))
                .filter(p -> spell.canTarget(game, p.getId()))
                .collect(Collectors.toList());

        if (eligibleCreatures.isEmpty()) {
            return Collections.emptyList();
        }

        FilterPermanent filter = new FilterCreaturePermanent("other creatures that " + spell.getName() + " could target");
        filter.add(Predicates.or(
                eligibleCreatures.stream()
                        .map(Permanent::getId)
                        .map(PermanentIdPredicate::new)
                        .collect(Collectors.toSet())
        ));

        TargetPermanent target = new TargetPermanent(0, eligibleCreatures.size(), filter, true);
        target.withNotTarget(true);
        target.withChooseHint("to copy " + spell.getName() + " for ({2} each)");
        target.chooseTarget(Outcome.Benefit, player.getId(), source, game);

        List<UUID> chosenIds = target.getTargets();
        if (chosenIds.isEmpty()) {
            return Collections.emptyList();
        }

        Cost cost = new GenericManaCost(2 * chosenIds.size());
        if (!cost.pay(source, game, source, player.getId(), false)) {
            return Collections.emptyList();
        }

        return chosenIds.stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(p -> new MageObjectReference(p, game))
                .map(MageObjectReferencePredicate::new)
                .collect(Collectors.toList());
    }
}
