
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;

/**
 *
 * @author Loki
 */
public final class FaithlessLooting extends CardImpl {

    public FaithlessLooting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");


        // Draw two cards, then discard two cards.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2,2));
        // Flashback {2}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{R}")));
    }

    private FaithlessLooting(final FaithlessLooting card) {
        super(card);
    }

    @Override
    public FaithlessLooting copy() {
        return new FaithlessLooting(this);
    }
}
