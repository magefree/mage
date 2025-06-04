package mage.cards.s;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.MoveCounterTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author anonymous
 */
public final class SimicGuildmage extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another target creature with the same controller");
    private static final FilterPermanent auraFilter = new FilterPermanent(SubType.AURA, "Aura enchanting a permanent");

    static {
        filter.add(new AnotherTargetPredicate(2));
        filter.add(SameControllerPredicate.instance);
        auraFilter.add(new AttachedToPredicate(StaticFilters.FILTER_PERMANENT));
    }

    public SimicGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/U}{G/U}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G}: Move a +1/+1 counter from target creature onto another target creature with the same controller.
        Ability countersAbility = new SimpleActivatedAbility(new MoveCounterTargetsEffect(CounterType.P1P1), new ManaCostsImpl<>("{1}{G}"));
        countersAbility.addTarget(new TargetCreaturePermanent().withChooseHint("to remove a counter from").setTargetTag(1));
        countersAbility.addTarget(new TargetPermanent(filter).withChooseHint("to move a counter to").setTargetTag(2));
        this.addAbility(countersAbility);

        // {1}{U}: Attach target Aura enchanting a permanent to another permanent with the same controller.
        Ability auraAbility = new SimpleActivatedAbility(new MoveAuraEffect(), new ManaCostsImpl<>("{1}{U}"));
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

enum SameControllerPredicate implements ObjectSourcePlayerPredicate<MageItem> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageItem> input, Game game) {
        StackObject source = game.getStack().getStackObject(input.getSourceId());
        if (source == null
                || source.getStackAbility().getTargets().isEmpty()
                || source.getStackAbility().getTargets().get(0).getTargets().isEmpty()) {
            return true;
        }
        Permanent firstTarget = game.getPermanent(source.getStackAbility().getTargets().get(0).getTargets().get(0));
        Permanent inputPermanent = game.getPermanent(input.getObject().getId());
        if (firstTarget == null || inputPermanent == null) {
            return true;
        }
        return firstTarget.isControlledBy(inputPermanent.getControllerId());
    }

    @Override
    public String toString() {
        return "Target with the same controller";
    }
}

class MoveAuraEffect extends OneShotEffect {

    MoveAuraEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Attach target Aura attached to a permanent to another permanent with the same controller.";
    }

    private MoveAuraEffect(final MoveAuraEffect effect) {
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
        Permanent aura = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || aura == null) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent(
                "permanent" + Optional
                        .of(aura)
                        .map(Permanent::getAttachedTo)
                        .map(game::getControllerId)
                        .map(game::getPlayer)
                        .map(Player::getName)
                        .map(s -> " controlled by" + s)
                        .orElse("")
        );
        filter.add(new ControllerIdPredicate(game.getControllerId(aura.getAttachedTo())));
        filter.add(Predicates.not(new PermanentIdPredicate(aura.getAttachedTo())));
        if (!game.getBattlefield().contains(filter, source.getControllerId(), source, game, 1)) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        controller.choose(outcome, target, source, game);
        return Optional
                .of(target)
                .map(TargetImpl::getFirstTarget)
                .map(game::getPermanent)
                .map(permanent -> permanent.addAttachment(aura.getId(), source, game))
                .orElse(false);
    }
}
