package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author NinthWorld
 */
public final class Ultralisk extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public Ultralisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Protection from noncreature
        this.addAbility(new ProtectionAbility(filter));
    }

    public Ultralisk(final Ultralisk card) {
        super(card);
    }

    @Override
    public Ultralisk copy() {
        return new Ultralisk(this);
    }
}
