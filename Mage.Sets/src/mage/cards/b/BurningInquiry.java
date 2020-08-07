package mage.cards.b;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author North
 */
public final class BurningInquiry extends CardImpl {

    public BurningInquiry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Each player draws three cards, then discards three cards at random.
        this.getSpellAbility().addEffect(new DrawCardAllEffect(3));
        Effect effect = new DiscardEachPlayerEffect(3, true);
        effect.setText(", then discards three cards at random");
        this.getSpellAbility().addEffect(effect);
    }

    private BurningInquiry(final BurningInquiry card) {
        super(card);
    }

    @Override
    public BurningInquiry copy() {
        return new BurningInquiry(this);
    }
}
