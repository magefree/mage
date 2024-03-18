package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author L_J
 */
public final class FickleEfreet extends CardImpl {

    public FickleEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.EFREET);

        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Whenever Fickle Efreet attacks or blocks, flip a coin at end of combat. If you lose the flip, an opponent gains control of Fickle Efreet.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new FickleEfreetChangeControlEffect()), true), false));
    }

    private FickleEfreet(final FickleEfreet card) {
        super(card);
    }

    @Override
    public FickleEfreet copy() {
        return new FickleEfreet(this);
    }
}

class FickleEfreetChangeControlEffect extends OneShotEffect {

    FickleEfreetChangeControlEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip a coin at end of combat. If you lose the flip, choose one of your opponents. That player gains control of {this}";
    }

    private FickleEfreetChangeControlEffect(final FickleEfreetChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public FickleEfreetChangeControlEffect copy() {
        return new FickleEfreetChangeControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || sourcePermanent == null) {
            return false;
        }
        if (!controller.flipCoin(source, game, true)) {
            Target target = new TargetOpponent(true);
            controller.chooseTarget(outcome, target, source, game);
            Player chosenOpponent = game.getPlayer(target.getFirstTarget());
            if (chosenOpponent != null) {
                game.addEffect(new GainControlTargetEffect(
                        Duration.Custom, true, chosenOpponent.getId()
                ).setTargetPointer(new FixedTarget(sourcePermanent, game)), source);
                game.informPlayers(chosenOpponent.getLogName() + " has gained control of " + sourcePermanent.getLogName());
                return true;
            }
        }
        return false;
    }
}
