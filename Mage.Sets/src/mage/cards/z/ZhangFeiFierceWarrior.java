
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class ZhangFeiFierceWarrior extends CardImpl {

    public ZhangFeiFierceWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER, SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance; horsemanship
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private ZhangFeiFierceWarrior(final ZhangFeiFierceWarrior card) {
        super(card);
    }

    @Override
    public ZhangFeiFierceWarrior copy() {
        return new ZhangFeiFierceWarrior(this);
    }
}
