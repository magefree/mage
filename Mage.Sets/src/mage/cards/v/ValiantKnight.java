package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ValiantKnight extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.KNIGHT, "Knights");

    public ValiantKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Other Knights you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostControlledEffect(
                        1, 1, Duration.WhileOnBattlefield,
                        filter, true
                )
        ));

        // {3}{W}{W}: Knights you control gain double strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilityControlledEffect(
                        DoubleStrikeAbility.getInstance(),
                        Duration.EndOfTurn, filter
                ), new ManaCostsImpl<>("{3}{W}{W}")
        ));
    }

    private ValiantKnight(final ValiantKnight card) {
        super(card);
    }

    @Override
    public ValiantKnight copy() {
        return new ValiantKnight(this);
    }
}
