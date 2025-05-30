
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class DrifterIlDal extends CardImpl {

    public DrifterIlDal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // At the beginning of your upkeep, sacrifice Drifter il-Dal unless you pay {U}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{U}"))));
    }

    private DrifterIlDal(final DrifterIlDal card) {
        super(card);
    }

    @Override
    public DrifterIlDal copy() {
        return new DrifterIlDal(this);
    }
}
