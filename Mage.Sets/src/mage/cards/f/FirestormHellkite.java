
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class FirestormHellkite extends CardImpl {

    public FirestormHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Cumulative upkeep {U}{R}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{U}{R}")));
    }

    private FirestormHellkite(final FirestormHellkite card) {
        super(card);
    }

    @Override
    public FirestormHellkite copy() {
        return new FirestormHellkite(this);
    }
}
