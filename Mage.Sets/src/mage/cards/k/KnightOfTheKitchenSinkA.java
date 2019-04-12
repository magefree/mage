
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.other.ExpansionSetPredicate;

/**
 *
 * @author Ketsuban
 */
public final class KnightOfTheKitchenSinkA extends CardImpl {

    private static final FilterObject filter = new FilterObject("black borders");

    static {
        filter.add(Predicates.or(
                new SupertypePredicate(SuperType.BASIC), // all Un-set basic lands are black bordered cards
                new NamePredicate("Steamflogger Boss"), // printed in Unstable with a black border
                Predicates.and(
                    Predicates.not(new ExpansionSetPredicate("UGL")),
                    Predicates.not(new ExpansionSetPredicate("UNH")),
                    Predicates.not(new ExpansionSetPredicate("UST"))
                )
        ));
    }

    public KnightOfTheKitchenSinkA(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.CYBORG);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Protection from black borders
        this.addAbility(new ProtectionAbility(filter));
    }

    public KnightOfTheKitchenSinkA(final KnightOfTheKitchenSinkA card) {
        super(card);
    }

    @Override
    public KnightOfTheKitchenSinkA copy() {
        return new KnightOfTheKitchenSinkA(this);
    }
}
