package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.GenericTargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladegriffPrototype extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent of that player's choice that one of your opponents controls");

    static {
        filter.add(BladegriffSourceOpponentControlsPredicate.instance);
    }

    public BladegriffPrototype(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Bladegriff Prototype deals combat damage to a player, destroy target nonland permanent of that player's choice that one of your opponents controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect(), false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new ThatPlayersChoiceTargetAdjuster());
        this.addAbility(ability);
    }

    private BladegriffPrototype(final BladegriffPrototype card) {
        super(card);
    }

    @Override
    public BladegriffPrototype copy() {
        return new BladegriffPrototype(this);
    }
}

enum BladegriffSourceOpponentControlsPredicate implements ObjectSourcePlayerPredicate<Controllable> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Controllable> input, Game game) {
        Controllable object = input.getObject();
        UUID playerId = input.getSource().getControllerId();

        return !object.isControlledBy(playerId)
                && game.getPlayer(playerId).hasOpponent(object.getControllerId(), game);
    }
}

class ThatPlayersChoiceTargetAdjuster extends GenericTargetAdjuster {
    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID opponentId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        ability.getTargets().clear();
        ability.getAllEffects().setTargetPointer(new FirstTargetPointer());
        Target newTarget = blueprintTarget.copy();
        newTarget.setTargetController(opponentId);
        ability.addTarget(newTarget);
    }
}
