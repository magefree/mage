package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.LanderToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KavLandseeker extends CardImpl {

    public KavLandseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature enters, create a Lander token. At the beginning of the end step on your next turn, sacrifice that token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KavLandseekerEffect()));
    }

    private KavLandseeker(final KavLandseeker card) {
        super(card);
    }

    @Override
    public KavLandseeker copy() {
        return new KavLandseeker(this);
    }
}

class KavLandseekerEffect extends OneShotEffect {

    KavLandseekerEffect() {
        super(Outcome.Benefit);
        staticText = "create a Lander token. "
                + "At the beginning of the end step on your next turn, sacrifice that token";
    }

    private KavLandseekerEffect(final KavLandseekerEffect effect) {
        super(effect);
    }

    @Override
    public KavLandseekerEffect copy() {
        return new KavLandseekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new LanderToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        game.addDelayedTriggeredAbility(new AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility(
                new SacrificeTargetEffect()
                        .setTargetPointer(new FixedTargets(token, game))
                        .setText("sacrifice that token"),
                GameEvent.EventType.END_TURN_STEP_PRE
        ), source);
        return true;
    }
}