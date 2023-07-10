
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author spjspj
 */
public final class SeerOfTheLastTomorrow extends CardImpl {

    public SeerOfTheLastTomorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {U}, {T}, Discard a card: Target player puts the top three cards of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsTargetEffect(3), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SeerOfTheLastTomorrow(final SeerOfTheLastTomorrow card) {
        super(card);
    }

    @Override
    public SeerOfTheLastTomorrow copy() {
        return new SeerOfTheLastTomorrow(this);
    }
}
