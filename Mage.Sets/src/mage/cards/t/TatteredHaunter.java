
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class TatteredHaunter extends CardImpl {

    public TatteredHaunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Tattered Haunter can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private TatteredHaunter(final TatteredHaunter card) {
        super(card);
    }

    @Override
    public TatteredHaunter copy() {
        return new TatteredHaunter(this);
    }
}
