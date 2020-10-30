
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class StompingGround extends CardImpl {

    public StompingGround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.FOREST);

        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(2)), "you may pay 2 life. If you don't, it enters the battlefield tapped"));
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    public StompingGround(final StompingGround card) {
        super(card);
    }

    @Override
    public StompingGround copy() {
        return new StompingGround(this);
    }

}
