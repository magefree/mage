package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldenTailDisciple extends CardImpl {

    public GoldenTailDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private GoldenTailDisciple(final GoldenTailDisciple card) {
        super(card);
    }

    @Override
    public GoldenTailDisciple copy() {
        return new GoldenTailDisciple(this);
    }
}
