
package mage.cards.s;

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
 * @author LoneFox
 */
public final class StrongholdZeppelin extends CardImpl {

    public StrongholdZeppelin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Stronghold Zeppelin can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private StrongholdZeppelin(final StrongholdZeppelin card) {
        super(card);
    }

    @Override
    public StrongholdZeppelin copy() {
        return new StrongholdZeppelin(this);
    }
}
