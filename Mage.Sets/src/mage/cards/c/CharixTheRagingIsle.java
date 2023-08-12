package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CharixTheRagingIsle extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spells");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.ISLAND);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2);
    private static final DynamicValue xValue2 = new MultipliedValue(xValue, -1);

    public CharixTheRagingIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LEVIATHAN);
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(0);
        this.toughness = new MageInt(17);

        // Spells your opponents cast that target Charix, the Raging Isle cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostModificationThatTargetSourceEffect(
                2, filter, TargetController.OPPONENT
        )));

        // {3}: Charix gets +X/-X until end of turn, where X is the number of Islands you control.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                xValue, xValue2, Duration.EndOfTurn, true
        ).setText("{this} gets +X/-X until end of turn, where X is the number of Islands you control"), new GenericManaCost(3)));
    }

    private CharixTheRagingIsle(final CharixTheRagingIsle card) {
        super(card);
    }

    @Override
    public CharixTheRagingIsle copy() {
        return new CharixTheRagingIsle(this);
    }
}
