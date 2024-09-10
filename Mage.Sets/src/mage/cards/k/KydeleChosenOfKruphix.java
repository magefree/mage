package mage.cards.k;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class KydeleChosenOfKruphix extends CardImpl {

    public KydeleChosenOfKruphix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Add {C} for each card you've drawn this turn.
        this.addAbility(new DynamicManaAbility(
                Mana.ColorlessMana(1), CardsDrawnThisTurnDynamicValue.instance,
                new TapSourceCost(), null, false, CardsDrawnThisTurnDynamicValue.instance
        ).addHint(CardsDrawnThisTurnDynamicValue.getHint()));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private KydeleChosenOfKruphix(final KydeleChosenOfKruphix card) {
        super(card);
    }

    @Override
    public KydeleChosenOfKruphix copy() {
        return new KydeleChosenOfKruphix(this);
    }
}
