package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class StromkirkCaptain extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.VAMPIRE, "Vampire creatures");

    public StromkirkCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        // Other Vampire creatures you control get +1/+1 and have first strike.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ).setText("and have first strike"));
        this.addAbility(ability);
    }

    private StromkirkCaptain(final StromkirkCaptain card) {
        super(card);
    }

    @Override
    public StromkirkCaptain copy() {
        return new StromkirkCaptain(this);
    }
}
