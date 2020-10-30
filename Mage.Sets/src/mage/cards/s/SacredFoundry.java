
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SacredFoundry extends CardImpl {

    public SacredFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.PLAINS);

        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(2)), "you may pay 2 life. If you don't, it enters the battlefield tapped"));
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    public SacredFoundry(final SacredFoundry card) {
        super(card);
    }

    @Override
    public SacredFoundry copy() {
        return new SacredFoundry(this);
    }

}
