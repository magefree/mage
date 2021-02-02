
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 *
 */
public final class SuChi extends CardImpl {

    public SuChi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Su-Chi dies, add {C}{C}{C}{C}.
        this.addAbility(new DiesSourceTriggeredAbility(new BasicManaEffect(Mana.ColorlessMana(4)), false));
    }

    private SuChi(final SuChi card) {
        super(card);
    }

    @Override
    public SuChi copy() {
        return new SuChi(this);
    }
}
