
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SpireSerpent extends CardImpl {

    private static final String abilityText1 = "<i>Metalcraft</i> &mdash; As long as you control three or more artifacts, {this} gets +2/+2";

    public SpireSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SERPENT);
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        this.addAbility(DefenderAbility.getInstance());
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), MetalcraftCondition.instance, abilityText1);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        Effect effect = new ConditionalAsThoughEffect(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                MetalcraftCondition.instance);
        effect.setText("and can attack as though it didn't have defender");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public SpireSerpent(final SpireSerpent card) {
        super(card);
    }

    @Override
    public SpireSerpent copy() {
        return new SpireSerpent(this);
    }
}
