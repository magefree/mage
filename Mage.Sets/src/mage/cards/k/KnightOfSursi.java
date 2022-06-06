
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlankingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class KnightOfSursi extends CardImpl {

    public KnightOfSursi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // flanking
        this.addAbility(new FlankingAbility());
        // Suspend 3-{W}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{W}"), this));
    }

    private KnightOfSursi(final KnightOfSursi card) {
        super(card);
    }

    @Override
    public KnightOfSursi copy() {
        return new KnightOfSursi(this);
    }
}
