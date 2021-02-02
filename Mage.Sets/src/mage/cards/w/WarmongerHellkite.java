
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author LevelX2
 */
public final class WarmongerHellkite extends CardImpl {

    public WarmongerHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // All creatures attack each combat if able.
        Effect effect = new AttacksIfAbleAllEffect(FILTER_PERMANENT_CREATURES, Duration.WhileOnBattlefield, true);
        effect.setText("All creatures attack each combat if able");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // {1}{R}: Attacking creatures get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 0, Duration.EndOfTurn, new FilterAttackingCreature("Attacking creatures"), false), new ManaCostsImpl("{1}{R}")));

    }

    private WarmongerHellkite(final WarmongerHellkite card) {
        super(card);
    }

    @Override
    public WarmongerHellkite copy() {
        return new WarmongerHellkite(this);
    }
}
