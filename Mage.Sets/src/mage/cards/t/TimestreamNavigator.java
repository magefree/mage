package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.costs.common.PutSourceOnBottomOwnerLibraryCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TimestreamNavigator extends CardImpl {

    public TimestreamNavigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ascend
        this.addAbility(new AscendAbility());

        // {2}{U}{U}, {T}, Put Timestream Navigator on the bottom of its owner's library: Take an extra turn after this one. Activate this ability only if you have the city's blessing.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new AddExtraTurnControllerEffect(),
                new ManaCostsImpl<>("{2}{U}{U}"),
                CitysBlessingCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addCost(new PutSourceOnBottomOwnerLibraryCost());
        ability.addHint(CitysBlessingHint.instance);
        this.addAbility(ability);

    }

    private TimestreamNavigator(final TimestreamNavigator card) {
        super(card);
    }

    @Override
    public TimestreamNavigator copy() {
        return new TimestreamNavigator(this);
    }
}
