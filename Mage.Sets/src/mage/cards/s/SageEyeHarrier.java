
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
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
public final class SageEyeHarrier extends CardImpl {

    public SageEyeHarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Morph {3}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{W}")));
    }

    private SageEyeHarrier(final SageEyeHarrier card) {
        super(card);
    }

    @Override
    public SageEyeHarrier copy() {
        return new SageEyeHarrier(this);
    }
}
