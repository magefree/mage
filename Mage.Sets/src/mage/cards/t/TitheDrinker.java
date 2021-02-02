
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExtortAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class TitheDrinker extends CardImpl {

    public TitheDrinker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Extort
        this.addAbility(new ExtortAbility());
    }

    private TitheDrinker(final TitheDrinker card) {
        super(card);
    }

    @Override
    public TitheDrinker copy() {
        return new TitheDrinker(this);
    }
}
