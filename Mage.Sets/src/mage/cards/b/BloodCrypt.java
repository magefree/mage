
package mage.cards.b;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class BloodCrypt extends CardImpl {

    public BloodCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.subtype.add(SubType.SWAMP, SubType.MOUNTAIN);

        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(2)), "you may pay 2 life. If you don't, it enters the battlefield tapped"));
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    public BloodCrypt(final BloodCrypt card) {
        super(card);
    }

    @Override
    public BloodCrypt copy() {
        return new BloodCrypt(this);
    }

}
