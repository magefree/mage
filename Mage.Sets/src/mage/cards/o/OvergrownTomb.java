
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class OvergrownTomb extends CardImpl {

    public OvergrownTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.FOREST);

        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(2)), "you may pay 2 life. If you don't, it enters the battlefield tapped"));
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    public OvergrownTomb(final OvergrownTomb card) {
        super(card);
    }

    @Override
    public OvergrownTomb copy() {
        return new OvergrownTomb(this);
    }

}
