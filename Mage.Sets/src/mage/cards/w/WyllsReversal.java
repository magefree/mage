package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetStackObject;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WyllsReversal extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("spell or ability with one or more targets");

    static {
        filter.add(WyllsReversalPredicate.instance);
    }

    public WyllsReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Choose target spell or ability with one or more targets. Roll a d20 and add the greatest power among creatures you control.
        // 1-14 | You may choose new targets for that spell or ability.
        // 15+ | You may choose new targets for that spell or ability. Then copy it. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new WyllsReversalEffect());
        this.getSpellAbility().addTarget(new TargetStackObject());
    }

    private WyllsReversal(final WyllsReversal card) {
        super(card);
    }

    @Override
    public WyllsReversal copy() {
        return new WyllsReversal(this);
    }
}

enum WyllsReversalPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return !input
                .getStackAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .allMatch(List::isEmpty);
    }
}

class WyllsReversalEffect extends RollDieWithResultTableEffect {

    WyllsReversalEffect() {
        super(20, "choose target spell or ability with one or more targets. " +
                "Roll a d20 and add the greatest power among creatures you control");
        this.addTableEntry(1, 14, new InfoEffect("you may choose new targets for that spell or ability"));
        this.addTableEntry(
                15, Integer.MAX_VALUE,
                new InfoEffect("you may choose new targets for that spell or ability. " +
                        "Then copy it. You may choose new targets for the copy"));
    }

    private WyllsReversalEffect(final WyllsReversalEffect effect) {
        super(effect);
    }

    @Override
    public WyllsReversalEffect copy() {
        return new WyllsReversalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        StackObject stackObject = game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        if (player == null || stackObject == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 20)
                + GreatestPowerAmongControlledCreaturesValue.instance.calculate(game, source, this);
        if (result >= 1) {
            stackObject.chooseNewTargets(
                    game, source.getControllerId(), false,
                    false, null
            );
        }
        if (result >= 14) {
            stackObject.createCopyOnStack(game, source, source.getControllerId(), true);
        }
        return true;
    }
}
