package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolidGround extends CardImpl {

    public SolidGround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // When this enchantment enters, earthbend 3.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(3));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);

        // If one or more +1/+1 counters would be put on a permanent you control, that many plus one +1/+1 counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(
                StaticFilters.FILTER_CONTROLLED_PERMANENT, CounterType.P1P1
        )));
    }

    private SolidGround(final SolidGround card) {
        super(card);
    }

    @Override
    public SolidGround copy() {
        return new SolidGround(this);
    }
}
