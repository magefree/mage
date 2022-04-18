
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class AvenSunstriker extends CardImpl {

    public AvenSunstriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Megamorph {4}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{4}{W}"), true));
    }

    private AvenSunstriker(final AvenSunstriker card) {
        super(card);
    }

    @Override
    public AvenSunstriker copy() {
        return new AvenSunstriker(this);
    }
}
