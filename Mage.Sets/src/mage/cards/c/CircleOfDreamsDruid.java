package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class CircleOfDreamsDruid extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);

    public CircleOfDreamsDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add {G} for each creature you control.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), xValue).addHint(new ValueHint("Creatures you control", xValue)));
    }

    private CircleOfDreamsDruid(final CircleOfDreamsDruid card) {
        super(card);
    }

    @Override
    public CircleOfDreamsDruid copy() {
        return new CircleOfDreamsDruid(this);
    }
}
