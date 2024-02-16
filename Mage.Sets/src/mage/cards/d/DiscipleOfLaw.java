

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class DiscipleOfLaw extends CardImpl {

    public DiscipleOfLaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private DiscipleOfLaw(final DiscipleOfLaw card) {
        super(card);
    }

    @Override
    public DiscipleOfLaw copy() {
        return new DiscipleOfLaw(this);
    }

}
