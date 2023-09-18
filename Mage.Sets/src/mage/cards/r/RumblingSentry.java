package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RumblingSentry extends CardImpl {

    public RumblingSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // When Rumbling Sentry enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));
    }

    private RumblingSentry(final RumblingSentry card) {
        super(card);
    }

    @Override
    public RumblingSentry copy() {
        return new RumblingSentry(this);
    }
}
