package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaryWatchdog extends CardImpl {

    public WaryWatchdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Wary Watchdog enters or dies, surveil 1.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new SurveilEffect(1), false));
    }

    private WaryWatchdog(final WaryWatchdog card) {
        super(card);
    }

    @Override
    public WaryWatchdog copy() {
        return new WaryWatchdog(this);
    }
}
