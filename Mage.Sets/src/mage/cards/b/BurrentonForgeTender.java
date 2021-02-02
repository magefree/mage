
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.PreventDamageBySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class BurrentonForgeTender extends CardImpl {

    private static final FilterObject filterObject = new FilterObject("a red");

    static {
        filterObject.add(new ColorPredicate(ObjectColor.RED));
    }

    public BurrentonForgeTender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.KITHKIN, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        
        // Sacrifice Burrenton Forge-Tender: Prevent all damage a red source of your choice would deal this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,  new PreventDamageBySourceEffect(filterObject), new SacrificeSourceCost()));

    }

    private BurrentonForgeTender(final BurrentonForgeTender card) {
        super(card);
    }

    @Override
    public BurrentonForgeTender copy() {
        return new BurrentonForgeTender(this);
    }
}
