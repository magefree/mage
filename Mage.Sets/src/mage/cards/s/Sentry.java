package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Sentry extends CardImpl {

    public Sentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {U}, {T}: Tap or untap another target creature.
    }

    public Sentry(final Sentry card) {
        super(card);
    }

    @Override
    public Sentry copy() {
        return new Sentry(this);
    }
}
