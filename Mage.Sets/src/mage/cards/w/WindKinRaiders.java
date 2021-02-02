
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class WindKinRaiders extends CardImpl {

    public WindKinRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private WindKinRaiders(final WindKinRaiders card) {
        super(card);
    }

    @Override
    public WindKinRaiders copy() {
        return new WindKinRaiders(this);
    }
}
