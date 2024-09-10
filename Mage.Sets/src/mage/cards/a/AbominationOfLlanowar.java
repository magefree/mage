package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class AbominationOfLlanowar extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ELF, "Elves you control");
    private static final FilterCard filter2
            = new FilterCard("plus the number of Elf cards");

    static {
        filter2.add(SubType.ELF.getPredicate());
    }

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(filter),
            new CardsInControllerGraveyardCount(filter2)
    );

    public AbominationOfLlanowar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Abomination of Llanowar's power and toughness are each equal to the number of Elves you control plus the number of Elf cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)
        ));
    }

    private AbominationOfLlanowar(final AbominationOfLlanowar card) {
        super(card);
    }

    @Override
    public AbominationOfLlanowar copy() {
        return new AbominationOfLlanowar(this);
    }
}
