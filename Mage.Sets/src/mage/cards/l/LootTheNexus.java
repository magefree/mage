package mage.cards.l;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LootTheNexus extends CardImpl {

    public LootTheNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Choose a color. Add one mana of that color for each different power among creatures you control.
        this.addAbility(new DynamicManaAbility(
            Mana.AnyMana(1), LootTheNexusValue.instance, new TapSourceCost(),
            "Choose a color. Add one mana of that color for each different power among creatures you control",
            true
        ).addHint(CovenHint.instance));
    }

    private LootTheNexus(final LootTheNexus card) {
        super(card);
    }

    @Override
    public LootTheNexus copy() {
        return new LootTheNexus(this);
    }
}


enum LootTheNexusValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .distinct()
                .map(x -> 1)
                .sum();
    }

    @Override
    public LootTheNexusValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "different power among creatures you control";
    }

    @Override
    public String toString() {
        return "1";
    }
}
