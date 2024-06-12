package mage.cards.a;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class AnotherRound extends CardImpl {

    public AnotherRound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{2}{W}");

        // Exile any number of creatures you control, then return them to the battlefield under their owner's control. Then repeat this process X times.
        this.getSpellAbility().addEffect(new AnotherRoundEffect());
    }

    private AnotherRound(final AnotherRound card) {
        super(card);
    }

    @Override
    public AnotherRound copy() {
        return new AnotherRound(this);
    }
}

class AnotherRoundEffect extends OneShotEffect {

    public AnotherRoundEffect() {
        super(Outcome.Benefit);
        staticText = "Exile any number of creatures you control, "
                + "then return them to the battlefield under their owner's control. "
                + "Then repeat this process X more times.";
    }

    private AnotherRoundEffect(final AnotherRoundEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int xValue = ManacostVariableValue.REGULAR.calculate(game, source, this);
        TargetControlledCreaturePermanent target =
                new TargetControlledCreaturePermanent(
                        0, Integer.MAX_VALUE,
                        StaticFilters.FILTER_CONTROLLED_CREATURE, true
                );

        for (int i = 0; i <= xValue; ++i) {
            target.clearChosen();
            controller.chooseTarget(Outcome.Benefit, target, source, game);
            new ExileThenReturnTargetEffect(false, true)
                    .setTargetPointer(new FixedTargets(
                            target.getTargets()
                                    .stream()
                                    .map(id -> new MageObjectReference(id, game))
                                    .collect(Collectors.toList())
                    )).apply(game, source);
            game.processAction();
        }
        return true;
    }

    @Override
    public AnotherRoundEffect copy() {
        return new AnotherRoundEffect(this);
    }
}
