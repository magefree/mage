package mage.cards.m;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MusterTheDeparted extends CardImpl {

    public MusterTheDeparted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Muster the Departed enters the battlefield, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken())));

        // Morbid -- At the beginning of your end step, if a creature died this turn, populate.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new PopulateEffect(), TargetController.YOU,
                MorbidCondition.instance, false
        ).setAbilityWord(AbilityWord.MORBID));
    }

    private MusterTheDeparted(final MusterTheDeparted card) {
        super(card);
    }

    @Override
    public MusterTheDeparted copy() {
        return new MusterTheDeparted(this);
    }
}
