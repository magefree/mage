package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TransformedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GargantuanSlabhorn extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("transformed permanents");

    static {
        filter.add(TransformedPredicate.instance);
    }

    public GargantuanSlabhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Other transformed permanents you control have trample and ward {2}.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(2)), Duration.WhileOnBattlefield, filter, true
        ).setText("and ward {2}"));
        this.addAbility(ability);
    }

    private GargantuanSlabhorn(final GargantuanSlabhorn card) {
        super(card);
    }

    @Override
    public GargantuanSlabhorn copy() {
        return new GargantuanSlabhorn(this);
    }
}
