package mage.cards.p;

import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhantomNinja extends CardImpl {

    public PhantomNinja(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Phantom Ninja can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private PhantomNinja(final PhantomNinja card) {
        super(card);
    }

    @Override
    public PhantomNinja copy() {
        return new PhantomNinja(this);
    }
}
