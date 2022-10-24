package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThePeregrineDynamo extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject(
            "activated or triggered ability you control from another legendary source that's not a commander"
    );

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(ThePeregrineDynamoPredicate.instance);
    }

    public ThePeregrineDynamo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {1}, {T}: Copy target activated or triggered ability you control from another legendary source that's not a commander. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new ThePeregrineDynamoEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter));
        this.addAbility(ability);
    }

    private ThePeregrineDynamo(final ThePeregrineDynamo card) {
        super(card);
    }

    @Override
    public ThePeregrineDynamo copy() {
        return new ThePeregrineDynamo(this);
    }
}

enum ThePeregrineDynamoPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        if (!(input.getObject() instanceof StackAbility)) {
            return false;
        }
        MageObject sourceObject = input.getSource().getSourceObject(game);
        return sourceObject != null
                && !sourceObject.getId().equals(input.getSourceId())
                && sourceObject.isLegendary()
                && !CommanderPredicate.instance.apply(sourceObject, game);
    }
}

class ThePeregrineDynamoEffect extends OneShotEffect {

    ThePeregrineDynamoEffect() {
        super(Outcome.Benefit);
        staticText = "copy target activated or triggered ability you control from another legendary source " +
                "that's not a commander. You may choose new targets for the copy";
    }

    private ThePeregrineDynamoEffect(final ThePeregrineDynamoEffect effect) {
        super(effect);
    }

    @Override
    public ThePeregrineDynamoEffect copy() {
        return new ThePeregrineDynamoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackAbility == null) {
            return false;
        }
        stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
        return true;
    }
}
