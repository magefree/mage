package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarScreecher extends CardImpl {

    public WarScreecher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {5}{W}, {T}: Other creatures you control get +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn, true
        ), new ManaCostsImpl<>("{5}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WarScreecher(final WarScreecher card) {
        super(card);
    }

    @Override
    public WarScreecher copy() {
        return new WarScreecher(this);
    }
}
