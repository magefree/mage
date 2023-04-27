
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class PhobianPhantasm extends CardImpl {

    public PhobianPhantasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying; fear
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FearAbility.getInstance());
        // Cumulative upkeep {B}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{B}")));
    }

    private PhobianPhantasm(final PhobianPhantasm card) {
        super(card);
    }

    @Override
    public PhobianPhantasm copy() {
        return new PhobianPhantasm(this);
    }
}
