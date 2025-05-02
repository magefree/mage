package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SacrificeOneOrMorePermanentsTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvinWaterdeepOpportunist extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.TREASURE));
    private static final DynamicValue twiceXValue = new MultipliedValue(xValue, 2);
    private static final Hint hint = new ValueHint("Treasures you control", xValue);

    public EvinWaterdeepOpportunist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Ward--Sacrifice a creature.
        this.addAbility(new WardAbility(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE), false));

        // Forge, Neverwinter Charlatan gets +2/+0 for each Treasure you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                twiceXValue, StaticValue.get(0), Duration.WhileOnBattlefield
        ).setText("{this} gets +2/+0 for each Treasure you control")).addHint(hint));

        // Whenever one or more players sacrifice one or more creatures, you create a tapped Treasure token. This ability triggers only once each turn.
        this.addAbility(new SacrificeOneOrMorePermanentsTriggeredAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new TreasureToken(), 1, true)
                        .setText("you create a tapped Treasure token"),
                StaticFilters.FILTER_PERMANENT_CREATURES, TargetController.ANY, SetTargetPointer.NONE, false
        ).setTriggersLimitEachTurn(1));
    }

    private EvinWaterdeepOpportunist(final EvinWaterdeepOpportunist card) {
        super(card);
    }

    @Override
    public EvinWaterdeepOpportunist copy() {
        return new EvinWaterdeepOpportunist(this);
    }
}
