package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class DwarvenProvisioner extends CardImpl {

    public DwarvenProvisioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{W}: Creatures you control get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BoostControlledEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{W}")
        ));
    }

    private DwarvenProvisioner(final DwarvenProvisioner card) {
        super(card);
    }

    @Override
    public DwarvenProvisioner copy() {
        return new DwarvenProvisioner(this);
    }
}
