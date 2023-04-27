package mage.cards.s;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class SimicGuildmage extends CardImpl {

    private static final FilterEnchantmentPermanent auraFilter = new FilterEnchantmentPermanent("Aura");

    static {
        auraFilter.add(SubType.AURA.getPredicate());
    }

    public SimicGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/U}{G/U}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G}: Move a +1/+1 counter from target creature onto another target creature with the same controller.
        Ability countersAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MoveCounterFromTargetToTargetEffect(), new ManaCostsImpl<>("{1}{G}"));
        TargetCreaturePermanent target = new TargetCreaturePermanent(
                new FilterCreaturePermanent("creature (you take counter from)"));
        target.setTargetTag(1);
        countersAbility.addTarget(target);

        FilterCreaturePermanent filter = new FilterCreaturePermanent(
                "another target creature with the same controller (counter goes to)");
        filter.add(new AnotherTargetPredicate(2));
        filter.add(new SameControllerPredicate());
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter);
        target2.setTargetTag(2);
        countersAbility.addTarget(target2);
        this.addAbility(countersAbility);

        // {1}{U}: Attach target Aura enchanting a permanent to another permanent with the same controller.
        Ability auraAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MoveAuraEffect(), new ManaCostsImpl<>("{1}{U}"));
        auraAbility.addTarget(new TargetPermanent(auraFilter));
        this.addAbility(auraAbility);

    }

    private SimicGuildmage(final SimicGuildmage card) {
        super(card);
    }

    @Override
    public SimicGuildmage copy() {
        return new SimicGuildmage(this);
    }
}

class MoveCounterFromTargetToTargetEffect extends OneShotEffect {

    public MoveCounterFromTargetToTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "Move a +1/+1 counter from target creature onto another target creature with the same controller";
    }

    public MoveCounterFromTargetToTargetEffect(final MoveCounterFromTargetToTargetEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterFromTargetToTargetEffect copy() {
        return new MoveCounterFromTargetToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent fromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            Permanent toPermanent = null;
            if (source.getTargets().size() > 1) {
                toPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            }
            if (fromPermanent == null || toPermanent == null || !fromPermanent.isControlledBy(toPermanent.getControllerId())) {
                return false;
            }
            fromPermanent.removeCounters(CounterType.P1P1.createInstance(1), source, game);
            toPermanent.addCounters(CounterType.P1P1.createInstance(1), source.getControllerId(), source, game);
            return true;
        }
        return false;

    }
}

class SameControllerPredicate implements ObjectSourcePlayerPredicate<MageItem> {

    @Override
    public boolean apply(ObjectSourcePlayer<MageItem> input, Game game) {
        StackObject source = game.getStack().getStackObject(input.getSourceId());
        if (source != null) {
            if (source.getStackAbility().getTargets().isEmpty()
                    || source.getStackAbility().getTargets().get(0).getTargets().isEmpty()) {
                return true;
            }
            Permanent firstTarget = game.getPermanent(
                    source.getStackAbility().getTargets().get(0).getTargets().get(0));
            Permanent inputPermanent = game.getPermanent(input.getObject().getId());
            if (firstTarget != null && inputPermanent != null) {
                return firstTarget.isControlledBy(inputPermanent.getControllerId());
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Target with the same controller";
    }

}

class MoveAuraEffect extends OneShotEffect {

    public MoveAuraEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Attach target Aura enchanting a permanent to another permanent with the same controller.";
    }

    public MoveAuraEffect(final MoveAuraEffect effect) {
        super(effect);
    }

    @Override
    public MoveAuraEffect copy() {
        return new MoveAuraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /*
            5/1/2006
            For the second ability, only the Aura is targeted.
            When the ability resolves, you choose a permanent to move the Aura onto.
            It can't be the permanent the Aura is already attached to, it must be controlled by the player who controls the permanent the Aura is attached to, and it must be able to be enchanted by the Aura.
            (It doesn't matter who controls the Aura or who controls Simic Guildmage.)
            If no such permanent exists, the Aura doesn't move.
         */

        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent aura = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (aura == null) {
                return true;
            }
            Permanent fromPermanent = game.getPermanent(aura.getAttachedTo());
            if (fromPermanent == null) {
                return false;
            }
            boolean passed = true;
            Target chosenPermanentToAttachAuras = aura.getSpellAbility().getTargets().get(0).copy();
            chosenPermanentToAttachAuras.setNotTarget(true);
            Filter filterChoice = chosenPermanentToAttachAuras.getFilter();
            filterChoice.add(new ControllerIdPredicate(fromPermanent.getControllerId()));
            filterChoice.add(Predicates.not(new PermanentIdPredicate(fromPermanent.getId())));
            chosenPermanentToAttachAuras.setTargetName("a different " + filterChoice.getMessage() + " with the same controller as the " + filterChoice.getMessage() + " the target aura is attached to");
            if (chosenPermanentToAttachAuras.canChoose(source.getControllerId(), source, game)
                    && controller.choose(Outcome.Neutral, chosenPermanentToAttachAuras, source, game)) {
                Permanent permanentToAttachAura = game.getPermanent(chosenPermanentToAttachAuras.getFirstTarget());
                if (permanentToAttachAura != null) {
                    // Check for protection
                    if (permanentToAttachAura.cantBeAttachedBy(aura, source, game, true)) {
                        passed = false;
                    }
                    if (passed) {
                        fromPermanent.removeAttachment(aura.getId(), source, game);
                        permanentToAttachAura.addAttachment(aura.getId(), source, game);
                        return true;
                    }
                }
            }
            return true;
        }

        return false;
    }
}
