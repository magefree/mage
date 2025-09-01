
package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author tcontis
 */
public final class WormfangNewt extends CardImpl {

    public WormfangNewt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Wormfang Turtle enters the battlefield, exile a land you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OneShotNonTargetEffect(
                new ExileTargetForSourceEffect().setText("exile a land you control"),
                new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_A_LAND))));

        // When Wormfang Turtle leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability2);
    }

    private WormfangNewt(final WormfangNewt card) {
        super(card);
    }

    @Override
    public WormfangNewt copy() {
        return new WormfangNewt(this);
    }
}