
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SaltRoadPatrol extends CardImpl {

    public SaltRoadPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Outlast {1}{W}
        this.addAbility(new OutlastAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private SaltRoadPatrol(final SaltRoadPatrol card) {
        super(card);
    }

    @Override
    public SaltRoadPatrol copy() {
        return new SaltRoadPatrol(this);
    }
}
