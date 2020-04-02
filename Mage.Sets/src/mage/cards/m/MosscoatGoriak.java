package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MosscoatGoriak extends CardImpl {

    public MosscoatGoriak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private MosscoatGoriak(final MosscoatGoriak card) {
        super(card);
    }

    @Override
    public MosscoatGoriak copy() {
        return new MosscoatGoriak(this);
    }
}
