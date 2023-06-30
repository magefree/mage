package mage.cards.z;

import mage.MageInt;
import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ZevlorElturelExile extends CardImpl {

    public ZevlorElturelExile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {2}, {T}: When you next cast an instant or sorcery spell that targets only a single opponent or a single permanent an opponent controls this turn, for each other opponent, choose that player or a permanent they control, copy that spell, and the copy targets the chosen permanent.
        Ability ability = new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new ZevlorElturelExileTriggeredAbility()), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ZevlorElturelExile(final ZevlorElturelExile card) {
        super(card);
    }

    @Override
    public ZevlorElturelExile copy() {
        return new ZevlorElturelExile(this);
    }
}

class ZevlorElturelExileTriggeredAbility extends DelayedTriggeredAbility {

    ZevlorElturelExileTriggeredAbility() {
        super(new ZevlorElturelExileEffect(), Duration.EndOfTurn, true, false);
    }

    private ZevlorElturelExileTriggeredAbility(final ZevlorElturelExileTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZevlorElturelExileTriggeredAbility copy() {
        return new ZevlorElturelExileTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!this.isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        Set<UUID> targets = spell
                .getSpellAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        targets.removeIf(uuid -> game.getPermanent(uuid) == null && game.getPlayer(uuid) == null);
        if (targets.size() != 1) {
            return false;
        }
        UUID targetId = targets.iterator().next();
        Set<UUID> opponents = game.getOpponents(this.getControllerId());
        if (opponents.contains(targetId)) {
            this.getEffects().setValue("targetId", targetId);
        } else if (opponents.contains(game.getControllerId(targetId))) {
            this.getEffects().setValue("targetId", game.getControllerId(targetId));
        } else {
            return false;
        }
        this.getEffects().setValue("savedSpell", spell);
        return true;
    }

    @Override
    public String getRule() {
        return "When you next cast an instant or sorcery spell that targets only a single opponent or a single permanent an opponent controls this turn, for each other opponent, choose that player or a permanent they control, copy that spell, and the copy targets the chosen player or permanent.";
    }
}

class ZevlorElturelExileEffect extends CopySpellForEachItCouldTargetEffect {

    ZevlorElturelExileEffect() {
        super();
    }

    @Override
    protected StackObject getStackObject(Game game, Ability source) {
        return (Spell) getValue("savedSpell");
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    protected List<MageObjectReferencePredicate> prepareCopiesWithTargets(StackObject stackObject, Player player, Ability source, Game game) {
        UUID targetId = (UUID) getValue("targetId");
        List<MageObjectReferencePredicate> predicates = new ArrayList<>();
        for (UUID opponentId : game.getOpponents(player.getId())) {
            if (opponentId.equals(targetId)) {
                continue;
            }
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            boolean canTargetPlayer = stackObject.canTarget(game, opponentId);
            Set<UUID> targetAb = game
                    .getBattlefield()
                    .getActivePermanents(
                            StaticFilters.FILTER_CONTROLLED_PERMANENT,
                            opponentId, source, game
                    )
                    .stream()
                    .map(MageItem::getId)
                    .filter(uuid -> stackObject.canTarget(game, uuid)).collect(Collectors.toSet());
            if (!canTargetPlayer && targetAb.isEmpty()) {
                continue;
            }
            if (canTargetPlayer && (targetAb.isEmpty() || player.chooseUse(
                    outcome, "Have the copy target " + opponent.getName() + " or a permanent they control?",
                    null, opponent.getName(), "A permanent they control", source, game
            ))) {
                predicates.add(new MageObjectReferencePredicate(new MageObjectReference(opponentId)));
            } else if (!targetAb.isEmpty()) {
                if (targetAb.size() == 1) {
                    predicates.add(new MageObjectReferencePredicate(targetAb.iterator().next(), game));
                    continue;
                }
                FilterPermanent filter = new FilterPermanent("Permanent to target");
                filter.add(Predicates.or(targetAb.stream().map(PermanentIdPredicate::new).collect(Collectors.toSet())));
                TargetPermanent target = new TargetPermanent(filter);
                target.setNotTarget(true);
                player.choose(outcome, target, source, game);
                predicates.add(new MageObjectReferencePredicate(target.getFirstTarget(), game));
            }
        }
        return predicates;
    }

    private ZevlorElturelExileEffect(final ZevlorElturelExileEffect effect) {
        super(effect);
    }

    @Override
    public ZevlorElturelExileEffect copy() {
        return new ZevlorElturelExileEffect(this);
    }
}
