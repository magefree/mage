
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class SpittingSliver extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All Sliver creatures");

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public SpittingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Sliver creatures have first strike.
        Ability gainedAbility = FirstStrikeAbility.getInstance();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(gainedAbility, Duration.WhileOnBattlefield, filter, "All Sliver creatures have first strike.")));
    }

    private SpittingSliver(final SpittingSliver card) {
        super(card);
    }

    @Override
    public SpittingSliver copy() {
        return new SpittingSliver(this);
    }
}
