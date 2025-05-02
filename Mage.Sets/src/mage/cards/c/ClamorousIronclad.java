package mage.cards.c;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClamorousIronclad extends CardImpl {

    public ClamorousIronclad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Crew 3
        this.addAbility(new CrewAbility(3));

        // Cycling {R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{R}")));
    }

    private ClamorousIronclad(final ClamorousIronclad card) {
        super(card);
    }

    @Override
    public ClamorousIronclad copy() {
        return new ClamorousIronclad(this);
    }
}
