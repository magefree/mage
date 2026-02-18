package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonaLisaScienceGeek extends CardImpl {

    public MonaLisaScienceGeek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {T}: Add X mana of any one color, where X is Mona Lisa's power.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), SourcePermanentPowerValue.NOT_NEGATIVE, new TapSourceCost(),
                "Add X mana of any one color, where X is {this}'s power", true
        ));
    }

    private MonaLisaScienceGeek(final MonaLisaScienceGeek card) {
        super(card);
    }

    @Override
    public MonaLisaScienceGeek copy() {
        return new MonaLisaScienceGeek(this);
    }
}
