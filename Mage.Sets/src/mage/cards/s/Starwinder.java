package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Starwinder extends CardImpl {

    public Starwinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Whenever a creature you control deals combat damage to a player, you may draw that many cards.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY),
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                true, SetTargetPointer.NONE, true
        ));

        // Warp {2}{U}{U}
        this.addAbility(new WarpAbility(this, "{2}{U}{U}"));
    }

    private Starwinder(final Starwinder card) {
        super(card);
    }

    @Override
    public Starwinder copy() {
        return new Starwinder(this);
    }
}
