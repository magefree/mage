
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class KagemaroFirstToSuffer extends CardImpl {

    public KagemaroFirstToSuffer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        DynamicValue xValue = CardsInControllerHandCount.instance;
        // Kagemaro, First to Suffer's power and toughness are each equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)));
        // {B}, Sacrifice Kagemaro: All creatures get -X/-X until end of turn, where X is the number of cards in your hand.

        DynamicValue xMinusValue = new SignInversionDynamicValue(xValue);
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostAllEffect(xMinusValue, xMinusValue, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false,
                        "All creatures get -X/-X until end of turn, where X is the number of cards in your hand"),
                new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private KagemaroFirstToSuffer(final KagemaroFirstToSuffer card) {
        super(card);
    }

    @Override
    public KagemaroFirstToSuffer copy() {
        return new KagemaroFirstToSuffer(this);
    }
}
