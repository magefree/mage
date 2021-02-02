
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author BursegSardaukar
 */
public final class BoggartSpriteChaser extends CardImpl {

    public BoggartSpriteChaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // As long as you control a Faerie, Boggart Sprite-Chaser gets +1/+1 and has flying.
        FilterPermanent filter = new FilterPermanent(SubType.FAERIE, "Faerie");
        Effect effect = new BoostSourceWhileControlsEffect(filter, 1, 1);
        effect.setText("As long as you control a Faerie, {this} gets +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter), "and has flying"));
        this.addAbility(ability);

    }

    private BoggartSpriteChaser(final BoggartSpriteChaser card) {
        super(card);
    }

    @Override
    public BoggartSpriteChaser copy() {
        return new BoggartSpriteChaser(this);
    }
}
