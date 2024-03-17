package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SentryBot extends CardImpl {

    public SentryBot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}");
        
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This spell costs {1} less to cast for each creature attacking you.
        // When Sentry Bot enters the battlefield, you get {E} for each creature attacking you.
        // At the beginning of combat on your turn, you may pay {E}{E}{E}. If you do, put a +1/+1 counter on each creature you control.
    }

    private SentryBot(final SentryBot card) {
        super(card);
    }

    @Override
    public SentryBot copy() {
        return new SentryBot(this);
    }
}
