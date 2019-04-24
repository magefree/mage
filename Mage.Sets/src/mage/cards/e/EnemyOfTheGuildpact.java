
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author Wehk
 */
public final class EnemyOfTheGuildpact extends CardImpl {

    private static final FilterObject filter = new FilterObject("multicolored");
    
    static {
        filter.add(new MulticoloredPredicate());
    }
    
    public EnemyOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Protection from multicolored
        this.addAbility(new ProtectionAbility(filter));
    }

    public EnemyOfTheGuildpact(final EnemyOfTheGuildpact card) {
        super(card);
    }

    @Override
    public EnemyOfTheGuildpact copy() {
        return new EnemyOfTheGuildpact(this);
    }
}
