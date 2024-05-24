package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class SuppressionRay extends ModalDoubleFacedCard {

    public SuppressionRay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{3}{W/U}{W/U}",
                "Orderly Plaza", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Suppression Ray
        // Sorcery

        // Tap all creatures target player controls. You may pay X {E}, then choose up to X creatures tapped this way. Put a stun counter on each of them.
        this.getLeftHalfCard().getSpellAbility().addEffect(new SuppressionRayTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetPlayer());

        // 2.
        // Orderly Plaza
        // Land

        // Orderly Plaza enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} or {U}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private SuppressionRay(final SuppressionRay card) {
        super(card);
    }

    @Override
    public SuppressionRay copy() {
        return new SuppressionRay(this);
    }
}

class SuppressionRayTargetEffect extends OneShotEffect {

    SuppressionRayTargetEffect() {
        super(Outcome.Tap);
        staticText = "Tap all creatures target player controls."
                + "You may pay X {E}, then choose up to X creatures tapped this way. "
                + "Put a stun counter on each of them.";
    }

    private SuppressionRayTargetEffect(final SuppressionRayTargetEffect effect) {
        super(effect);
    }

    @Override
    public SuppressionRayTargetEffect copy() {
        return new SuppressionRayTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return false;
        }
        List<MageObjectReference> tappedThisWay = new ArrayList<>();
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
            if (!creature.isTapped()) {
                creature.tap(source, game);
                if (creature.isTapped()) {
                    tappedThisWay.add(new MageObjectReference(creature, game));
                }
            }
        }
        int maxEnergy = SourceControllerCountersCount.ENERGY.calculate(game, source, this);
        if (maxEnergy == 0) {
            // No energy to pay. Stop there.
            return true;
        }
        int numberToPay = controller.getAmount(
                0, maxEnergy,
                "How many {E} do you like to pay? (" + tappedThisWay.size() + " creature(s) were tapped)",
                game
        );
        if (numberToPay == 0) {
            // No energy was paid.
            return true;
        }
        Cost cost = new PayEnergyCost(numberToPay);
        if (cost.pay(source, game, source, source.getControllerId(), true)) {
            FilterPermanent filter = new FilterPermanent("creature tapped this way");
            filter.add(new PermanentReferenceInCollectionPredicate(tappedThisWay));
            TargetPermanent target = new TargetPermanent(0, numberToPay, filter, true);
            target.choose(Outcome.Tap, controller.getId(), source.getSourceId(), source, game);
            List<MageObjectReference> toPutStunCounterOn = target
                    .getTargets()
                    .stream()
                    .map(id -> new MageObjectReference(id, game))
                    .collect(Collectors.toList());
            if (!toPutStunCounterOn.isEmpty()) {
                new AddCountersTargetEffect(CounterType.STUN.createInstance())
                        .setTargetPointer(new FixedTargets(toPutStunCounterOn))
                        .apply(game, source);
            }
        }
        return true;
    }
}