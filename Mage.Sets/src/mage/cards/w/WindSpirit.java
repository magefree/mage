
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WindSpirit extends CardImpl {

    public WindSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());
    }

    private WindSpirit(final WindSpirit card) {
        super(card);
    }

    @Override
    public WindSpirit copy() {
        return new WindSpirit(this);
    }
}
