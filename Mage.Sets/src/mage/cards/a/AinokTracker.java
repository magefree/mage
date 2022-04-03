
package mage.cards.a;

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
 * @author LevelX2
 */
public final class AinokTracker extends CardImpl {

    public AinokTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Morph 4R
        this.addAbility(new MorphAbility(new ManaCostsImpl("{4}{R}")));
    }

    private AinokTracker(final AinokTracker card) {
        super(card);
    }

    @Override
    public AinokTracker copy() {
        return new AinokTracker(this);
    }
}
