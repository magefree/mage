
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterTeamPermanent;

/**
 *
 * @author TheElk801
 */
public final class SylviaBrightspear extends CardImpl {

    private static final FilterTeamPermanent filter = new FilterTeamPermanent(SubType.DRAGON, "Dragons your team controls");

    public SylviaBrightspear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Khorvath Brightflame (When this creature enters the battlefield, target player may put Khorvath into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Khorvath Brightflame", true));

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Dragons your team controls have double strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    private SylviaBrightspear(final SylviaBrightspear card) {
        super(card);
    }

    @Override
    public SylviaBrightspear copy() {
        return new SylviaBrightspear(this);
    }
}
