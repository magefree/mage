package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SheoldredsHeadcleaver extends CardImpl {

    public SheoldredsHeadcleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Toxic 2
        this.addAbility(new ToxicAbility(2));
    }

    private SheoldredsHeadcleaver(final SheoldredsHeadcleaver card) {
        super(card);
    }

    @Override
    public SheoldredsHeadcleaver copy() {
        return new SheoldredsHeadcleaver(this);
    }
}
