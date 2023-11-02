package mage.cards.l;

import mage.MageInt;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LunarHatchling extends CardImpl {

    public LunarHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));

        // Escape-{4}{G}{U}, Exile a land you control, Exile five other cards from your graveyard.
        CostsImpl<Cost> additionalCost = new CostsImpl();
        additionalCost.add(new ExileTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_A_LAND)));
        this.addAbility(new EscapeAbility(this, "{4}{G}{U}", 5, additionalCost));
    }

    private LunarHatchling(final LunarHatchling card) {
        super(card);
    }

    @Override
    public LunarHatchling copy() {
        return new LunarHatchling(this);
    }
}
