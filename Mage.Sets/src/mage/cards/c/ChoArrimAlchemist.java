
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class ChoArrimAlchemist extends CardImpl {

    public ChoArrimAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}{W}, {T}, Discard a card: The next time a source of your choice would deal damage to you this turn, prevent that damage. You gain life equal to the damage prevented this way.
        Ability ability = new SimpleActivatedAbility(
                new PreventNextDamageFromChosenSourceEffect(
                        Duration.EndOfTurn, true,
                        PreventNextDamageFromChosenSourceEffect.ON_PREVENT_YOU_GAIN_THAT_MUCH_LIFE
                ),
                new ManaCostsImpl<>("{1}{W}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private ChoArrimAlchemist(final ChoArrimAlchemist card) {
        super(card);
    }

    @Override
    public ChoArrimAlchemist copy() {
        return new ChoArrimAlchemist(this);
    }
}