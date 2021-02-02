
package mage.cards.b;

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
public final class BelbesPercher extends CardImpl {

    public BelbesPercher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Belbe's Percher can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private BelbesPercher(final BelbesPercher card) {
        super(card);
    }

    @Override
    public BelbesPercher copy() {
        return new BelbesPercher(this);
    }
}
