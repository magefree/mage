
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LoneFox
 */
public final class RiptideBiologist extends CardImpl {

    private static final FilterCard filter = new FilterCard("Beasts");

    static {
        filter.add(new SubtypePredicate(SubType.BEAST));
    }

    public RiptideBiologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Protection from Beasts
        this.addAbility(new ProtectionAbility(filter));
        // Morph {2}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{U}")));
    }

    public RiptideBiologist(final RiptideBiologist card) {
        super(card);
    }

    @Override
    public RiptideBiologist copy() {
        return new RiptideBiologist(this);
    }
}
