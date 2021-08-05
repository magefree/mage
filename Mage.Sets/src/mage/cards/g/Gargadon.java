package mage.cards.g;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Gargadon extends CardImpl {

    public Gargadon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Suspend 4—{1}{R}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{1}{R}"), this));
    }

    private Gargadon(final Gargadon card) {
        super(card);
    }

    @Override
    public Gargadon copy() {
        return new Gargadon(this);
    }
}
