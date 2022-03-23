package mage.cards.r;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.util.TargetAddress;

import java.util.*;

/**
 * @author duncant
 */
public final class Radiate extends CardImpl {

    protected static final FilterSpell filter = new FilterInstantOrSorcerySpell(
            "instant or sorcery spell that targets only a single permanent or player"
    );

    static {
        filter.add(SpellWithOnlySingleTargetPredicate.instance);
        filter.add(SpellWithOnlyPermanentOrPlayerTargetsPredicate.instance);
    }

    public Radiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Choose target instant or sorcery spell that targets only a single permanent or player. Copy that spell for each other permanent or player the spell could target. Each copy targets a different one of those permanents and players.
        this.getSpellAbility().addEffect(new RadiateEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private Radiate(final Radiate card) {
        super(card);
    }

    @Override
    public Radiate copy() {
        return new Radiate(this);
    }
}

enum SpellWithOnlySingleTargetPredicate implements ObjectSourcePlayerPredicate<Spell> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Spell> input, Game game) {
        Spell spell = input.getObject();
        if (spell == null) {
            return false;
        }
        UUID singleTarget = null;
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID targetId : targetInstance.getTargets()) {
                if (singleTarget == null) {
                    singleTarget = targetId;
                } else if (!singleTarget.equals(targetId)) {
                    return false;
                }
            }
        }
        return singleTarget != null;
    }
}

enum SpellWithOnlyPermanentOrPlayerTargetsPredicate implements ObjectSourcePlayerPredicate<Spell> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Spell> input, Game game) {
        Spell spell = input.getObject();
        if (spell == null) {
            return false;
        }
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID targetId : targetInstance.getTargets()) {
                if (game.getPermanent(targetId) == null
                        && game.getPlayer(targetId) == null) {
                    return false;
                }
            }
        }
        return true;
    }
}

class RadiateEffect extends CopySpellForEachItCouldTargetEffect {

    RadiateEffect() {
        super();
        staticText = "Choose target instant or sorcery spell that targets only a single permanent or player. " +
                "Copy that spell for each other permanent or player the spell could target. " +
                "Each copy targets a different one of those permanents and players.";
    }

    private RadiateEffect(RadiateEffect effect) {
        super(effect);
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    protected List<MageObjectReferencePredicate> prepareCopiesWithTargets(StackObject stackObject, Player player, Ability source, Game game) {
        List<MageObjectReferencePredicate> predicates = new ArrayList<>();
        UUID targeted = ((Spell) stackObject)
                .getSpellAbilities()
                .stream()
                .map(AbilityImpl::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
        game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT, player.getId(), source, game
                ).stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.equals(game.getPermanent(targeted)))
                .filter(p -> stackObject.canTarget(game, p.getId()))
                .map(p -> new MageObjectReference(p, game))
                .map(MageObjectReferencePredicate::new)
                .forEach(predicates::add);
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .filter(uuid -> !uuid.equals(targeted))
                .filter(uuid -> stackObject.canTarget(game, uuid))
                .map(MageObjectReference::new)
                .map(MageObjectReferencePredicate::new)
                .forEach(predicates::add);
        return predicates;
    }

    @Override
    protected Spell getStackObject(Game game, Ability source) {
        return game.getSpell(source.getFirstTarget());
    }

    @Override
    public RadiateEffect copy() {
        return new RadiateEffect(this);
    }
}
