package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class HandOfVecna extends CardImpl {

    public HandOfVecna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // At the beginning of combat on your turn, equipped creature or a creature you control named Vecna gets +X/+X until end of turn, where X is the number of cards in your hand.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new HandOfVecnaEffect(), TargetController.YOU, false
        ));

        // Equip—Pay 1 life for each card in your hand.
        this.addAbility(new EquipAbility(Outcome.Benefit, new PayLifeCost(
                CardsInControllerHandCount.instance, "1 life for each card in your hand"
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private HandOfVecna(final HandOfVecna card) {
        super(card);
    }

    @Override
    public HandOfVecna copy() {
        return new HandOfVecna(this);
    }
}

class HandOfVecnaEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control named Vecna");

    static {
        filter.add(new NamePredicate("Vecna"));
    }

    HandOfVecnaEffect() {
        super(Outcome.Benefit);
        staticText = "equipped creature or a creature you control " +
                "named Vecna gets +X/+X until end of turn, " +
                "where X is the number of cards in your hand";
    }

    private HandOfVecnaEffect(final HandOfVecnaEffect effect) {
        super(effect);
    }

    @Override
    public HandOfVecnaEffect copy() {
        return new HandOfVecnaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().size() < 1) {
            return false;
        }
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent equipped = game.getPermanent(sourcePermanent != null ? sourcePermanent.getAttachedTo() : null);
        List<Permanent> chooseable = game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        );
        if (equipped != null) {
            chooseable.add(equipped);
        }
        Permanent toBoost;
        switch (chooseable.size()) {
            case 0:
                return false;
            case 1:
                toBoost = chooseable.get(0);
                break;
            default:
                FilterPermanent filter = new FilterPermanent("a creature to give +X/+X to");
                filter.add(Predicates.or(
                        chooseable
                                .stream()
                                .map(permanent -> new MageObjectReferencePredicate(permanent, game))
                                .collect(Collectors.toList())
                ));
                TargetPermanent target = new TargetPermanent(filter);
                target.setNotTarget(true);
                player.choose(outcome, target, source, game);
                toBoost = game.getPermanent(target.getFirstTarget());
        }
        int xValue = player.getHand().size();
        game.addEffect(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(toBoost, game)), source);
        return true;
    }
}
