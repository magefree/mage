package mage.cards.r;

import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReptilianReflection extends CardImpl {

    public ReptilianReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever you cycle a card, Reptilian Reflection becomes a 5/4 Dinosaur creature with trample and haste in addition to its other types until end of turn.
        this.addAbility(new CycleControllerTriggeredAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(5, 4, "5/4 Dinosaur creature with trample and haste", SubType.DINOSAUR)
                    .withAbility(TrampleAbility.getInstance()).withAbility(HasteAbility.getInstance()),
                CardType.ENCHANTMENT,
                Duration.EndOfTurn
            ), true
        ));
    }

    private ReptilianReflection(final ReptilianReflection card) {
        super(card);
    }

    @Override
    public ReptilianReflection copy() {
        return new ReptilianReflection(this);
    }
}
