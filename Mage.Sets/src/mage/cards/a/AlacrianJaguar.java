package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlacrianJaguar extends CardImpl {

    public AlacrianJaguar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever this creature attacks while saddled, it gets +2/+2 until end of turn.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn, "it")
        ));

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private AlacrianJaguar(final AlacrianJaguar card) {
        super(card);
    }

    @Override
    public AlacrianJaguar copy() {
        return new AlacrianJaguar(this);
    }
}
