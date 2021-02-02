
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class FellFlagship extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Pirates");

    static {
        filter.add(SubType.PIRATE.getPredicate());
    }

    public FellFlagship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Pirates you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter)));

        // Whenever Fell Flagship deals combat damage to a player, that player discards a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1, false), false, true));

        // Crew 3
        this.addAbility(new CrewAbility(3));

    }

    private FellFlagship(final FellFlagship card) {
        super(card);
    }

    @Override
    public FellFlagship copy() {
        return new FellFlagship(this);
    }
}
