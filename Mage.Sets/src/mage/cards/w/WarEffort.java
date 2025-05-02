package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.RedWarriorToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarEffort extends CardImpl {

    public WarEffort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield)));

        // Whenever you attack, create a 1/1 red Warrior creature token that's tapped and attacking. Sacrifice it at the beginning of the next end step.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new WarEffortEffect(), 1));
    }

    private WarEffort(final WarEffort card) {
        super(card);
    }

    @Override
    public WarEffort copy() {
        return new WarEffort(this);
    }
}

class WarEffortEffect extends OneShotEffect {

    WarEffortEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 red Warrior creature token that's tapped and attacking. " +
                "Sacrifice it at the beginning of the next end step";
    }

    private WarEffortEffect(final WarEffortEffect effect) {
        super(effect);
    }

    @Override
    public WarEffortEffect copy() {
        return new WarEffortEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new RedWarriorToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice it").setTargetPointer(new FixedTargets(token, game))
        ), source);
        return true;
    }
}
