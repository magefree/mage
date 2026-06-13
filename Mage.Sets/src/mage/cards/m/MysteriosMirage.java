package mage.cards.m;

import java.util.UUID;

import mage.abilities.condition.common.ControllerDiscardedThisTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.ControllerDiscardedHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.IllusionVillainToken;

/**
 *
 * @author muz
 */
public final class MysteriosMirage extends CardImpl {

    public MysteriosMirage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your end step, if you discarded a card this turn, create a 3/3 blue Illusion Villain creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.YOU, new CreateTokenEffect(new IllusionVillainToken()),
            false, ControllerDiscardedThisTurnCondition.instance
        ).addHint(ControllerDiscardedHint.instance));
    }

    private MysteriosMirage(final MysteriosMirage card) {
        super(card);
    }

    @Override
    public MysteriosMirage copy() {
        return new MysteriosMirage(this);
    }
}
