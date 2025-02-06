package mage.cards.o;

import mage.MageInt;
import mage.Mana;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.common.ActivatedAbilityManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmenHawker extends CardImpl {

    public OmenHawker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {C}{U}. Spend this many only to activate abilities.
        this.addAbility(new ConditionalColoredManaAbility(
                new Mana(0, 1, 0, 0, 0, 0, 0, 1), new ActivatedAbilityManaBuilder()
        ));
    }

    private OmenHawker(final OmenHawker card) {
        super(card);
    }

    @Override
    public OmenHawker copy() {
        return new OmenHawker(this);
    }
}
