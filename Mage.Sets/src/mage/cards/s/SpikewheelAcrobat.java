package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpikewheelAcrobat extends CardImpl {

    public SpikewheelAcrobat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Spectacle {2}{R}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{2}{R}")));
    }

    private SpikewheelAcrobat(final SpikewheelAcrobat card) {
        super(card);
    }

    @Override
    public SpikewheelAcrobat copy() {
        return new SpikewheelAcrobat(this);
    }
}
