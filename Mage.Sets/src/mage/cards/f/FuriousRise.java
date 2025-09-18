package mage.cards.f;

import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.ExileTopCardPlayUntilExileAnotherEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FuriousRise extends CardImpl {

    public FuriousRise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of your end step, if you control a creature with power 4 or greater, exile the top card of your library.
        // You may play that card until you exile another card with Furious Rise.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ExileTopCardPlayUntilExileAnotherEffect())
                .withInterveningIf(FerociousCondition.instance).addHint(FerociousHint.instance));
    }

    private FuriousRise(final FuriousRise card) {
        super(card);
    }

    @Override
    public FuriousRise copy() {
        return new FuriousRise(this);
    }
}
