package mage.cards.c;

import mage.MageInt;
import mage.Mana;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.keyword.PrototypeAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CradleClearcutter extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public CradleClearcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Prototype {2}{G} - 1/3
        this.addAbility(new PrototypeAbility(this, "{2}{G}", 1, 3));

        // {T}: Add an amount of {G} equal to Cradle Clearcutter's power.
        this.addAbility(new DynamicManaAbility(
                Mana.GreenMana(1), xValue, "Add an amount of {G} equal to {this}'s power."
        ));
    }

    private CradleClearcutter(final CradleClearcutter card) {
        super(card);
    }

    @Override
    public CradleClearcutter copy() {
        return new CradleClearcutter(this);
    }
}
