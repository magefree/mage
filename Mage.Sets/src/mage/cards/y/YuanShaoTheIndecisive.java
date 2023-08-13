
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAllEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class YuanShaoTheIndecisive extends CardImpl {

    public YuanShaoTheIndecisive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        
        // Each creature you control can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneAllEffect(new FilterControlledCreaturePermanent())));
    }

    private YuanShaoTheIndecisive(final YuanShaoTheIndecisive card) {
        super(card);
    }

    @Override
    public YuanShaoTheIndecisive copy() {
        return new YuanShaoTheIndecisive(this);
    }
}
