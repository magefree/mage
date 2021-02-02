
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class DormantSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SLIVER, "All Sliver creatures");

    private static final FilterPermanent filterSlivers = new FilterPermanent(SubType.SLIVER, "All Slivers");

    public DormantSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{U}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Sliver creatures have defender.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(DefenderAbility.getInstance(), Duration.WhileOnBattlefield, filter, "All Sliver creatures have defender.")));
        // All Slivers have "When this permanent enters the battlefield, draw a card."
        Ability ability2 = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability2, Duration.WhileOnBattlefield, filter, "All Slivers have \"When this permanent enters the battlefield, draw a card.\"")));
    }

    private DormantSliver(final DormantSliver card) {
        super(card);
    }

    @Override
    public DormantSliver copy() {
        return new DormantSliver(this);
    }
}
