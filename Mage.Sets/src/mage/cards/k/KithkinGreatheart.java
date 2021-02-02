
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KithkinGreatheart extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Giant");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.GIANT.getPredicate());
    }
    private static final String rule2 = "As long as you control a Giant, {this} has first strike";

    public KithkinGreatheart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as you control a Giant, Kithkin Greatheart gets +1/+1 and has first strike.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1,1, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter),
                "As long as you control a Giant, Kithkin Greatheart gets +1/+1"));
        this.addAbility(ability);
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),  
                new PermanentsOnTheBattlefieldCondition(filter),
                rule2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect2));

    }

    private KithkinGreatheart(final KithkinGreatheart card) {
        super(card);
    }

    @Override
    public KithkinGreatheart copy() {
        return new KithkinGreatheart(this);
    }
}
