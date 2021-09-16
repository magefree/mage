
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardHandDrawSameNumberSourceEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;

/**
 *
 * @author North
 */
public final class ShatteredPerception extends CardImpl {

    public ShatteredPerception(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Discard all the cards in your hand, then draw that many cards.
        this.getSpellAbility().addEffect(new DiscardHandDrawSameNumberSourceEffect());
        // Flashback {5}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{R}")));
    }

    private ShatteredPerception(final ShatteredPerception card) {
        super(card);
    }

    @Override
    public ShatteredPerception copy() {
        return new ShatteredPerception(this);
    }
}
