package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class MirkwoodMeditator extends CardImpl {

    public MirkwoodMeditator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Landfall -- Whenever a land you control enters, you may have this creature's base power and toughness become 4/2 until end of turn.
        this.addAbility(new LandfallAbility(
            new SetBasePowerToughnessSourceEffect(4, 2, Duration.EndOfTurn)
                .setText("have this creature's base power and toughness become 4/2 until end of turn"),
            true
        ));
    }

    private MirkwoodMeditator(final MirkwoodMeditator card) {
        super(card);
    }

    @Override
    public MirkwoodMeditator copy() {
        return new MirkwoodMeditator(this);
    }
}
