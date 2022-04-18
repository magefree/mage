
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class BranchsnapLorian extends CardImpl {

    public BranchsnapLorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Morph {G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{G}")));
    }

    private BranchsnapLorian(final BranchsnapLorian card) {
        super(card);
    }

    @Override
    public BranchsnapLorian copy() {
        return new BranchsnapLorian(this);
    }
}
