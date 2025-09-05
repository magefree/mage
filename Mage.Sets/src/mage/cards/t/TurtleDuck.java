package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurtleDuck extends CardImpl {

    public TurtleDuck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {3}: Until end of turn, this creature has base power 4 and gains trample.
        Ability ability = new SimpleActivatedAbility(new SetBasePowerSourceEffect(4, Duration.EndOfTurn)
                .setText("until end of turn, this creature has base power 4"), new GenericManaCost(3));
        ability.addEffect(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample"));
        this.addAbility(ability);
    }

    private TurtleDuck(final TurtleDuck card) {
        super(card);
    }

    @Override
    public TurtleDuck copy() {
        return new TurtleDuck(this);
    }
}
