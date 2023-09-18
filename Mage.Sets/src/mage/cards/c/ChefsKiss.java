package mage.cards.c;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.util.RandomUtil;
import mage.util.TargetAddress;
import mage.util.functions.StackObjectCopyApplier;

import java.util.*;

/**
 * @author TheElk801
 */
public final class ChefsKiss extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("spell that targets only a single permanent or player");

    static {
        filter.add(ChefsKissPredicate.instance);
    }

    public ChefsKiss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // Gain control of target spell that targets only a single permanent or player. Copy it, then reselect the targets at random for the spell and the copy. The new targets can't be you or a permanent you control.
        this.getSpellAbility().addEffect(new ChefsKissEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private ChefsKiss(final ChefsKiss card) {
        super(card);
    }

    @Override
    public ChefsKiss copy() {
        return new ChefsKiss(this);
    }
}

enum ChefsKissPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        Spell spell = (Spell) input;
        Set<UUID> targets = new HashSet<>();
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID target : targetInstance.getTargets()) {
                if (game.getPermanent(target) != null || game.getPlayer(target) != null) {
                    targets.add(target);
                }
                if (targets.size() > 1) {
                    return false;
                }
            }
        }
        return targets.size() == 1;
    }
}

class ChefsKissEffect extends OneShotEffect {

    ChefsKissEffect() {
        super(Outcome.Benefit);
        staticText = "gain control of target spell that targets only a single permanent or player. " +
                "Copy it, then reselect the targets at random for the spell and the copy. " +
                "The new targets can't be you or a permanent you control";
    }

    private ChefsKissEffect(final ChefsKissEffect effect) {
        super(effect);
    }

    @Override
    public ChefsKissEffect copy() {
        return new ChefsKissEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getFirstTarget());
        if (spell == null) {
            return false;
        }
        spell.setControllerId(source.getControllerId());
        List<UUID> possibleTargets = new ArrayList<>(game.getState().getPlayersInRange(source.getControllerId(), game));
        possibleTargets.remove(source.getControllerId());
        game.getBattlefield()
                .getActivePermanents(source.getControllerId(), game)
                .stream()
                .filter(p -> !p.isControlledBy(source.getControllerId()))
                .map(MageItem::getId)
                .forEach(possibleTargets::add);
        possibleTargets.removeIf(uuid -> !spell.canTarget(game, uuid));
        StackObjectCopyApplier applier;
        MageObjectReferencePredicate predicate;
        if (possibleTargets.isEmpty()) {
            applier = null;
            predicate = null;
        } else {
            applier = new ChefsKissApplier(possibleTargets.get(RandomUtil.nextInt(possibleTargets.size())), game);
            predicate = new MageObjectReferencePredicate(new MageObjectReference(
                    possibleTargets.get(RandomUtil.nextInt(possibleTargets.size())), game
            ));
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), false, 1, applier);
        if (predicate != null) {
            spell.chooseNewTargets(game, source.getControllerId(), true, true, predicate);
        }
        return true;
    }
}

class ChefsKissApplier implements StackObjectCopyApplier {

    private final Iterator<MageObjectReferencePredicate> predicate;

    ChefsKissApplier(UUID targetId, Game game) {
        this.predicate = Arrays.asList(new MageObjectReferencePredicate(
                new MageObjectReference(targetId, game)
        )).iterator();
    }

    @Override
    public void modifySpell(StackObject stackObject, Game game) {
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        if (predicate.hasNext()) {
            return predicate.next();
        }
        return null;
    }
}
