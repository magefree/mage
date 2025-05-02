package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki (Mortivore), cbt33
 */
public final class Cantivore extends CardImpl {

    private static final DynamicValue xValue = new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_ENCHANTMENTS);
    private static final Hint hint = new ValueHint("Enchantment cards in all graveyards", xValue);

    public Cantivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Cantivore's power and toughness are each equal to the number of enchantment cards in all graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)).addHint(hint));
    }

    private Cantivore(final Cantivore card) {
        super(card);
    }

    @Override
    public Cantivore copy() {
        return new Cantivore(this);
    }
}
