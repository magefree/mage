package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AbominationWorldRavager extends CardImpl {

    public AbominationWorldRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Menace
        this.addAbility(new MenaceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Mayhem {4}{R}
        this.addAbility(new MayhemAbility(this, "{4}{R}"));
    }

    private AbominationWorldRavager(final AbominationWorldRavager card) {
        super(card);
    }

    @Override
    public AbominationWorldRavager copy() {
        return new AbominationWorldRavager(this);
    }
}
