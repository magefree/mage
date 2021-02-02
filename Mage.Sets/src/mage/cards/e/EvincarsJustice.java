
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class EvincarsJustice extends CardImpl {

    public EvincarsJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        
        // Evincar's Justice deals 2 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(2));
    }

    private EvincarsJustice(final EvincarsJustice card) {
        super(card);
    }

    @Override
    public EvincarsJustice copy() {
        return new EvincarsJustice(this);
    }
}
