package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class DevkarinDissident extends CardImpl {

    public DevkarinDissident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{G}: Devkarin Dissident gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new ManaCostsImpl<>("{4}{G}")
        ));
    }

    private DevkarinDissident(final DevkarinDissident card) {
        super(card);
    }

    @Override
    public DevkarinDissident copy() {
        return new DevkarinDissident(this);
    }
}
