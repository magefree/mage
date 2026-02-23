package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EndBlazeEpiphany extends CardImpl {

    public EndBlazeEpiphany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // End-Blaze Epiphany deals X damage to target creature. When that creature dies this turn, exile a number of cards from the top of your library equal to its power, then choose a card exiled this way. Until the end of your next turn, you may play that card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new ExileTopXMayPlayUntilEffect(
                        EndBlazeEpiphanyValue.instance, true, Duration.UntilEndOfYourNextTurn
                ).setText("exile a number of cards from the top of your library equal to its power, " +
                        "then choose a card exiled this way. Until the end of your next turn, you may play that card"))
        ));
    }

    private EndBlazeEpiphany(final EndBlazeEpiphany card) {
        super(card);
    }

    @Override
    public EndBlazeEpiphany copy() {
        return new EndBlazeEpiphany(this);
    }
}

enum EndBlazeEpiphanyValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Permanent) effect.getValue("creatureDied"))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public EndBlazeEpiphanyValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
