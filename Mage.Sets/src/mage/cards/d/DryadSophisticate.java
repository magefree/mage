
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class DryadSophisticate extends CardImpl {

    public DryadSophisticate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Nonbasic landwalk
        this.addAbility(new LandwalkAbility(FilterLandPermanent.nonbasicLand()));
    }

    public DryadSophisticate(final DryadSophisticate card) {
        super(card);
    }

    @Override
    public DryadSophisticate copy() {
        return new DryadSophisticate(this);
    }
}
