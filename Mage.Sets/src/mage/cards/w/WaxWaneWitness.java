package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.GainLoseLifeYourTurnTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class WaxWaneWitness extends CardImpl {

    public WaxWaneWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you gain or lose life during your turn, Wax-Wane Witness gets +1/+0 until end of turn.
        this.addAbility(new GainLoseLifeYourTurnTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn)
        ));
    }

    private WaxWaneWitness(final WaxWaneWitness card) {
        super(card);
    }

    @Override
    public WaxWaneWitness copy() {
        return new WaxWaneWitness(this);
    }
}
