package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Shriekdiver extends CardImpl {

    public Shriekdiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}: Shriekdiver gains haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ), new GenericManaCost(1)));
    }

    private Shriekdiver(final Shriekdiver card) {
        super(card);
    }

    @Override
    public Shriekdiver copy() {
        return new Shriekdiver(this);
    }
}
