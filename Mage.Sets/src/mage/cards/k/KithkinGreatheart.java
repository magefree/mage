package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KithkinGreatheart extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Giant");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.GIANT.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public KithkinGreatheart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as you control a Giant, Kithkin Greatheart gets +1/+1 and has first strike.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                condition, "As long as you control a Giant, {this} gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                condition, "and has first strike"
        ));
        this.addAbility(ability);
    }

    private KithkinGreatheart(final KithkinGreatheart card) {
        super(card);
    }

    @Override
    public KithkinGreatheart copy() {
        return new KithkinGreatheart(this);
    }
}
