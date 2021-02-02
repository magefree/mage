
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class MysticSpeculation extends CardImpl {

    public MysticSpeculation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Buyback {2}
        this.addAbility(new BuybackAbility("{2}"));

        // Scry 3.
        this.getSpellAbility().addEffect(new ScryEffect(3));
    }

    private MysticSpeculation(final MysticSpeculation card) {
        super(card);
    }

    @Override
    public MysticSpeculation copy() {
        return new MysticSpeculation(this);
    }
}
