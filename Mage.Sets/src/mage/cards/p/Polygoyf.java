package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessPlusOneSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author grimreap124
 */
public final class Polygoyf extends CardImpl {

    private static final DynamicValue powerValue = CardTypesInGraveyardCount.ALL;

    public Polygoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.LHURGOYF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Myriad
        this.addAbility(new MyriadAbility());

        // Polygoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessPlusOneSourceEffect(powerValue)));
    }

    private Polygoyf(final Polygoyf card) {
        super(card);
    }

    @Override
    public Polygoyf copy() {
        return new Polygoyf(this);
    }
}
