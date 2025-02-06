package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WalkingSarcophagus extends CardImpl {

    public WalkingSarcophagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- This creature gets +1/+2.
        this.addAbility(new MaxSpeedAbility(new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield)));
    }

    private WalkingSarcophagus(final WalkingSarcophagus card) {
        super(card);
    }

    @Override
    public WalkingSarcophagus copy() {
        return new WalkingSarcophagus(this);
    }
}
