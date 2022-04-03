
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SaguMauler extends CardImpl {

    public SaguMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Morph {3}{G}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{3}{G}{U}")));
    }

    private SaguMauler(final SaguMauler card) {
        super(card);
    }

    @Override
    public SaguMauler copy() {
        return new SaguMauler(this);
    }
}
