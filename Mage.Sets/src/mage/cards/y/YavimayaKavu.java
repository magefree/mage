
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.common.continuous.SetBaseToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class YavimayaKavu extends CardImpl {
  
    private static final FilterCreaturePermanent filterGreenCreature = new FilterCreaturePermanent("green creatures on the battlefield");
    private static final FilterCreaturePermanent filterRedCreature = new FilterCreaturePermanent("red creatures on the battlefield");
    
    static {
      filterGreenCreature.add(new ColorPredicate(ObjectColor.GREEN));
      filterRedCreature.add(new ColorPredicate(ObjectColor.RED));
    }

    public YavimayaKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        

        // Yavimaya Kavu's power is equal to the number of red creatures on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(new PermanentsOnBattlefieldCount(filterRedCreature))));
        // Yavimaya Kavu's toughness is equal to the number of green creatures on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBaseToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterGreenCreature))));
    }

    private YavimayaKavu(final YavimayaKavu card) {
        super(card);
    }

    @Override
    public YavimayaKavu copy() {
        return new YavimayaKavu(this);
    }
}
