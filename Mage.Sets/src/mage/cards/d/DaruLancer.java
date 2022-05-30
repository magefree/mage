
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class DaruLancer extends CardImpl {

    public DaruLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Morph {2}{W}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{W}{W}")));
    }

    private DaruLancer(final DaruLancer card) {
        super(card);
    }

    @Override
    public DaruLancer copy() {
        return new DaruLancer(this);
    }
}
