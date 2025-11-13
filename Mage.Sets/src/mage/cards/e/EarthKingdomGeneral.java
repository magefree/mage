package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutCounterOnPermanentTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthKingdomGeneral extends CardImpl {

    private static final DynamicValue xValue = new EffectKeyValue("countersAdded", "that many");

    public EarthKingdomGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, earthbend 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(2));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);

        // Whenever you put one or more +1/+1 counters on a creature, you may gain that much life. Do this only once each turn.
        this.addAbility(new PutCounterOnPermanentTriggeredAbility(
                new GainLifeEffect(xValue), CounterType.P1P1, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setDoOnlyOnceEachTurn(true));
    }

    private EarthKingdomGeneral(final EarthKingdomGeneral card) {
        super(card);
    }

    @Override
    public EarthKingdomGeneral copy() {
        return new EarthKingdomGeneral(this);
    }
}
