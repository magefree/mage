
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DuskriderPeregrine extends CardImpl {

    public DuskriderPeregrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // Suspend 3-{1}{W}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{1}{W}"), this));
    }

    private DuskriderPeregrine(final DuskriderPeregrine card) {
        super(card);
    }

    @Override
    public DuskriderPeregrine copy() {
        return new DuskriderPeregrine(this);
    }
}
