package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarriedDronesmith extends CardImpl {

    public HarriedDronesmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, create a 1/1 colorless Thopter artifact creature token with flying. It gains haste until end of turn. Sacrifice it at the beginning of your next end step.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new HarriedDronesmithEffect(), TargetController.YOU, false
        ));
    }

    private HarriedDronesmith(final HarriedDronesmith card) {
        super(card);
    }

    @Override
    public HarriedDronesmith copy() {
        return new HarriedDronesmith(this);
    }
}

class HarriedDronesmithEffect extends OneShotEffect {

    HarriedDronesmithEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 colorless Thopter artifact creature token with flying. " +
                "It gains haste until end of turn. Sacrifice it at the beginning of your next end step";
    }

    private HarriedDronesmithEffect(final HarriedDronesmithEffect effect) {
        super(effect);
    }

    @Override
    public HarriedDronesmithEffect copy() {
        return new HarriedDronesmithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new ThopterColorlessToken();
        token.putOntoBattlefield(1, game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect()
                        .setTargetPointer(new FixedTargets(token, game))
                        .setText("sacrifice it"),
                TargetController.YOU
        ), source);
        return true;
    }
}
