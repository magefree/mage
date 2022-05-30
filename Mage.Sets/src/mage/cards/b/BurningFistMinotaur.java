
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class BurningFistMinotaur extends CardImpl {

    public BurningFistMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.MINOTAUR, SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {1}{R}, Discard a card: Burning-Fist Minotaur gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private BurningFistMinotaur(final BurningFistMinotaur card) {
        super(card);
    }

    @Override
    public BurningFistMinotaur copy() {
        return new BurningFistMinotaur(this);
    }
}
