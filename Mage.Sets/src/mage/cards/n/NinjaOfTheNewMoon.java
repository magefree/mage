package mage.cards.n;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NinjaOfTheNewMoon extends CardImpl {

    public NinjaOfTheNewMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Ninjutsu {3}{B}
        this.addAbility(new NinjutsuAbility("{3}{B}"));
    }

    private NinjaOfTheNewMoon(final NinjaOfTheNewMoon card) {
        super(card);
    }

    @Override
    public NinjaOfTheNewMoon copy() {
        return new NinjaOfTheNewMoon(this);
    }
}
