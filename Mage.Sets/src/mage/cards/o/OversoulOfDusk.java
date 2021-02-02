
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class OversoulOfDusk extends CardImpl {

    private static final FilterCard filter = new FilterCard("blue, from black, and from red");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)));
    }

    public OversoulOfDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G/W}{G/W}{G/W}{G/W}{G/W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Protection from blue, from black, and from red
        this.addAbility(new ProtectionAbility(filter));
    }

    private OversoulOfDusk(final OversoulOfDusk card) {
        super(card);
    }

    @Override
    public OversoulOfDusk copy() {
        return new OversoulOfDusk(this);
    }
}
