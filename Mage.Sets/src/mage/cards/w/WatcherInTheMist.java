package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class WatcherInTheMist extends CardImpl {

    public WatcherInTheMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Watcher in the Mist enters the battlefield, surveil 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SurveilEffect(2), false
        ));
    }

    private WatcherInTheMist(final WatcherInTheMist card) {
        super(card);
    }

    @Override
    public WatcherInTheMist copy() {
        return new WatcherInTheMist(this);
    }
}
