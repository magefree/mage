package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObscuraInterceptor extends CardImpl {

    public ObscuraInterceptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Obscura Interceptor enters the battlefield, it connives. When it connives this way, return up to one target spell to its owner's hand.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetSpell(0, 1, StaticFilters.FILTER_SPELL));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConniveSourceEffect("it", ability)));
    }

    private ObscuraInterceptor(final ObscuraInterceptor card) {
        super(card);
    }

    @Override
    public ObscuraInterceptor copy() {
        return new ObscuraInterceptor(this);
    }
}
