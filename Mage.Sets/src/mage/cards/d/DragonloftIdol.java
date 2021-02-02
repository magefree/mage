
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DragonloftIdol extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.DRAGON.getPredicate());
    }

    public DragonloftIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as you control a Dragon, Dragonloft Idol gets +1/+1 and has flying and trample.

        Effect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1,1, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter),
                "As long as you control a Dragon, Dragonloft Idol gets +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter),
                "and has flying");
        ability.addEffect(effect);
        effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter),
                "and trample");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private DragonloftIdol(final DragonloftIdol card) {
        super(card);
    }

    @Override
    public DragonloftIdol copy() {
        return new DragonloftIdol(this);
    }
}
