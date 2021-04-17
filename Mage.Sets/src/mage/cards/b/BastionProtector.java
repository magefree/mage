
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

/**
 *
 * @author LevelX2
 */
public final class BastionProtector extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Commander creatures");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public BastionProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Commander creatures you control get +2/+2 and have indestructible.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter));
        Effect effect = new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and have indestructible");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private BastionProtector(final BastionProtector card) {
        super(card);
    }

    @Override
    public BastionProtector copy() {
        return new BastionProtector(this);
    }
}
