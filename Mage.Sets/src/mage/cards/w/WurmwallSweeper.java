package mage.cards.w;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WurmwallSweeper extends CardImpl {

    public WurmwallSweeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, surveil 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(2)));

        // Station
        this.addAbility(new StationAbility());

        // STATION 4+
        // Flying
        // 2/2
        this.addAbility(new StationLevelAbility(4)
                .withLevelAbility(FlyingAbility.getInstance())
                .withPT(2, 2));
    }

    private WurmwallSweeper(final WurmwallSweeper card) {
        super(card);
    }

    @Override
    public WurmwallSweeper copy() {
        return new WurmwallSweeper(this);
    }
}
