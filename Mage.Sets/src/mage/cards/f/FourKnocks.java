package mage.cards.f;

import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FourKnocks extends CardImpl {

    public FourKnocks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Vanishing 4
        this.addAbility(new VanishingAbility(4));

        // At the beginning of your precombat main phase, draw a card.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new DrawCardSourceControllerEffect(1), TargetController.YOU, false
        ));
    }

    private FourKnocks(final FourKnocks card) {
        super(card);
    }

    @Override
    public FourKnocks copy() {
        return new FourKnocks(this);
    }
}
