package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class Lavaleaper extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("a player taps a basic land");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    public Lavaleaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // All creatures have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ALL_CREATURES
        )));

        // Whenever a player taps a basic land for mana, that player adds one mana of any type that land produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(), filter, SetTargetPointer.PERMANENT
        ));
    }

    private Lavaleaper(final Lavaleaper card) {
        super(card);
    }

    @Override
    public Lavaleaper copy() {
        return new Lavaleaper(this);
    }
}
