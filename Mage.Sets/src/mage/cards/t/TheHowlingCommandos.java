package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TheHowlingCommandos extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SOLDIER, "Soldiers");

    public TheHowlingCommandos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {5}: Creatures you control get +1/+1 until end of turn. Soldiers you control gain vigilance until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(5));
        ability.addEffect(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, filter));
        this.addAbility(ability);
    }

    private TheHowlingCommandos(final TheHowlingCommandos card) {
        super(card);
    }

    @Override
    public TheHowlingCommandos copy() {
        return new TheHowlingCommandos(this);
    }
}
