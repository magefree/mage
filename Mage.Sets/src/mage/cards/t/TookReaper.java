package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TookReaper extends CardImpl {

    public TookReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Took Reaper dies, the Ring tempts you.
        this.addAbility(new DiesSourceTriggeredAbility(new TheRingTemptsYouEffect()));
    }

    private TookReaper(final TookReaper card) {
        super(card);
    }

    @Override
    public TookReaper copy() {
        return new TookReaper(this);
    }
}
