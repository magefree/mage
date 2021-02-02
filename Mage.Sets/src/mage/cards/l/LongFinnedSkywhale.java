
package mage.cards.l;

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
 * @author fireshoes
 */
public final class LongFinnedSkywhale extends CardImpl {

    public LongFinnedSkywhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Long-Finned Skywhale can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private LongFinnedSkywhale(final LongFinnedSkywhale card) {
        super(card);
    }

    @Override
    public LongFinnedSkywhale copy() {
        return new LongFinnedSkywhale(this);
    }
}
