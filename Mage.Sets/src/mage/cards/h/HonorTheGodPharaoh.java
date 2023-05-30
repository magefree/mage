package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class HonorTheGodPharaoh extends CardImpl {

    public HonorTheGodPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost(false));

        // Draw two cards. Amass 1.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).setText("draw two cards"));
        this.getSpellAbility().addEffect(new AmassEffect(1, SubType.ZOMBIE));
    }

    private HonorTheGodPharaoh(final HonorTheGodPharaoh card) {
        super(card);
    }

    @Override
    public HonorTheGodPharaoh copy() {
        return new HonorTheGodPharaoh(this);
    }
}
