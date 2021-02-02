
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class AlabasterDragon extends CardImpl {

    public AlabasterDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Alabaster Dragon dies, shuffle it into its owner's library.
        this.addAbility(new DiesSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));                                                                                          }

    private AlabasterDragon(final AlabasterDragon card) {
        super(card);
    }

    @Override
    public AlabasterDragon copy() {
        return new AlabasterDragon(this);
    }
}
