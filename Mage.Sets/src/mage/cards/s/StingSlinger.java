package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StingSlinger extends CardImpl {

    public StingSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{R}, {T}, Blight 1: This creature deals 2 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new BlightCost(1));
        this.addAbility(ability);
    }

    private StingSlinger(final StingSlinger card) {
        super(card);
    }

    @Override
    public StingSlinger copy() {
        return new StingSlinger(this);
    }
}
