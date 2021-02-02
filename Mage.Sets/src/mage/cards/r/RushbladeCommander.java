
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.SubType;
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
public final class RushbladeCommander extends CardImpl {

    private static final FilterTeamPermanent filter = new FilterTeamPermanent(SubType.WARRIOR, "Warriors your team controls");

    public RushbladeCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.AZRA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Warrior creatures your team controls have haste.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(
                        HasteAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        filter
                )
        ));
    }

    private RushbladeCommander(final RushbladeCommander card) {
        super(card);
    }

    @Override
    public RushbladeCommander copy() {
        return new RushbladeCommander(this);
    }
}
