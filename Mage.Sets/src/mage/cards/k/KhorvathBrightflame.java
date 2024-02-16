
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
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
public final class KhorvathBrightflame extends CardImpl {

    private static final FilterTeamPermanent filter = new FilterTeamPermanent(SubType.KNIGHT, "Knights your team controls");

    public KhorvathBrightflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Partner with Sylvia Brightspear (When this creature enters the battlefield, target player may put Sylvia into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Sylvia Brightspear", true));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Knights your team controls have flying and haste.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter));
        ability.addEffect(new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter).setText("and haste"));
        this.addAbility(ability);
    }

    private KhorvathBrightflame(final KhorvathBrightflame card) {
        super(card);
    }

    @Override
    public KhorvathBrightflame copy() {
        return new KhorvathBrightflame(this);
    }
}
