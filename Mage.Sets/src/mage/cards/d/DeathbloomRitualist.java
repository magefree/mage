package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
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
public final class DeathbloomRitualist extends CardImpl {

    private static final CardsInControllerGraveyardCount count = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
    private static final ValueHint hint = new ValueHint("Creature cards in your graveyard", count);

    public DeathbloomRitualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {T}: Add X mana of any one color, where X is the number of creature cards in your graveyard.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), count, new TapSourceCost(),
                "Add X mana of any one color, where X is the number of creature cards in your graveyard",
                true
        ).addHint(hint));
    }

    private DeathbloomRitualist(final DeathbloomRitualist card) {
        super(card);
    }

    @Override
    public DeathbloomRitualist copy() {
        return new DeathbloomRitualist(this);
    }
}
