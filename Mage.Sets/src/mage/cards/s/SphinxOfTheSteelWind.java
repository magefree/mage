

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SphinxOfTheSteelWind extends CardImpl {

    public SphinxOfTheSteelWind (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}{W}{U}{B}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(ProtectionAbility.from(ObjectColor.RED, ObjectColor.GREEN));
    }

    private SphinxOfTheSteelWind(final SphinxOfTheSteelWind card) {
        super(card);
    }

    @Override
    public SphinxOfTheSteelWind copy() {
        return new SphinxOfTheSteelWind(this);
    }

}
