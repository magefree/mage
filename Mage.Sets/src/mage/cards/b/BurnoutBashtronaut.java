package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
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
public final class BurnoutBashtronaut extends CardImpl {

    public BurnoutBashtronaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // {2}: This creature gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new GenericManaCost(2)
        ));

        // Max speed -- This creature has double strike.
        this.addAbility(new MaxSpeedAbility(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance())));
    }

    private BurnoutBashtronaut(final BurnoutBashtronaut card) {
        super(card);
    }

    @Override
    public BurnoutBashtronaut copy() {
        return new BurnoutBashtronaut(this);
    }
}
