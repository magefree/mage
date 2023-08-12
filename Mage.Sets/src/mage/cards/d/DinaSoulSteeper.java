package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DinaSoulSteeper extends CardImpl {

    public DinaSoulSteeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you gain life, each opponent loses 1 life.
        this.addAbility(new GainLifeControllerTriggeredAbility(new LoseLifeOpponentsEffect(1), false));

        // {1}, Sacrifice another creature: Dina, Soul Steeper gets +X/+0 until end of turn, where X is the sacrificed creature's power.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                SacrificeCostCreaturesPower.instance,
                StaticValue.get(0), Duration.EndOfTurn
        ), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        )));
        this.addAbility(ability);
    }

    private DinaSoulSteeper(final DinaSoulSteeper card) {
        super(card);
    }

    @Override
    public DinaSoulSteeper copy() {
        return new DinaSoulSteeper(this);
    }
}
