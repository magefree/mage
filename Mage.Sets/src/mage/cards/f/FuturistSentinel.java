package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FuturistSentinel extends CardImpl {

    public FuturistSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private FuturistSentinel(final FuturistSentinel card) {
        super(card);
    }

    @Override
    public FuturistSentinel copy() {
        return new FuturistSentinel(this);
    }
}
