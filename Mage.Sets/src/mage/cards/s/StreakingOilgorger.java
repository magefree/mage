package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
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
public final class StreakingOilgorger extends CardImpl {

    public StreakingOilgorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- This creature has lifelink.
        this.addAbility(new MaxSpeedAbility(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield
        )));
    }

    private StreakingOilgorger(final StreakingOilgorger card) {
        super(card);
    }

    @Override
    public StreakingOilgorger copy() {
        return new StreakingOilgorger(this);
    }
}
