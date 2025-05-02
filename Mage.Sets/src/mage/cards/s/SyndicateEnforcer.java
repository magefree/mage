
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SyndicateEnforcer extends CardImpl {

    public SyndicateEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        this.addAbility(new ExtortAbility());
    }

    private SyndicateEnforcer(final SyndicateEnforcer card) {
        super(card);
    }

    @Override
    public SyndicateEnforcer copy() {
        return new SyndicateEnforcer(this);
    }
}
