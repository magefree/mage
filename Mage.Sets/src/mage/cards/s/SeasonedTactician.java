
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromTopOfLibraryCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox

 */
public final class SeasonedTactician extends CardImpl {

    public SeasonedTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {3}, Exile the top four cards of your library: The next time a source of your choice would deal damage to you this turn, prevent that damage.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventNextDamageFromChosenSourceToYouEffect(Duration.EndOfTurn),
            new ManaCostsImpl<>("{3}"));
        ability.addCost(new ExileFromTopOfLibraryCost(4));
        this.addAbility(ability);
    }

    private SeasonedTactician(final SeasonedTactician card) {
        super(card);
    }

    @Override
    public SeasonedTactician copy() {
        return new SeasonedTactician(this);
    }
}
