package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenomsacLagac extends CardImpl {

    public VenomsacLagac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever this creature attacks while saddled, it gets +0/+3 until end of turn.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(
                new BoostSourceEffect(0, 3, Duration.EndOfTurn, "it")
        ));

        // Saddle 2
        this.addAbility(new SaddleAbility(2));
    }

    private VenomsacLagac(final VenomsacLagac card) {
        super(card);
    }

    @Override
    public VenomsacLagac copy() {
        return new VenomsacLagac(this);
    }
}
